package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryData;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpot;
import site.dittotrip.ditto_trip.hashtag.repository.HashtagRepository;
import site.dittotrip.ditto_trip.quest.aop.ExpHandlingTargetMethod;
import site.dittotrip.ditto_trip.quest.aop.QuestHandlingTargetMethod;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.review.utils.DistanceCalculator;
import site.dittotrip.ditto_trip.spot.domain.*;
import site.dittotrip.ditto_trip.spot.domain.dto.*;
import site.dittotrip.ditto_trip.spot.exception.NoUserPointInfoException;
import site.dittotrip.ditto_trip.spot.exception.NotMatchedSortException;
import site.dittotrip.ditto_trip.spot.exception.SpotVisitDistanceException;
import site.dittotrip.ditto_trip.spot.repository.*;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.RedisService;
import site.dittotrip.ditto_trip.utils.S3Service;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotService {

    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryBookmarkRepository categoryBookmarkRepository;
    private final CategorySpotRepository categorySpotRepository;
    private final StillCutRepository stillCutRepository;
    private final ReviewRepository reviewRepository;
    private final SpotBookmarkRepository spotBookmarkRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final AlarmRepository alarmRepository;
    private final HashtagRepository hashtagRepository;
    private final S3Service s3Service;
    private final RedisService redisService;

    public SpotCategoryListRes findSpotListInCategory(Long reqUserId, Long categoryId,
                                                      Double userX, Double userY, Pageable pageable) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        Page<CategorySpot> page = null;
        PageRequest newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        String sortString = pageable.getSort().toString();

        switch (sortString) {
            case "createdDateTime: DESC" ->
                    page = categorySpotRepository.findByCategoryOrderByCreated(category, newPageable);
            case "rating: DESC" ->
                    page = categorySpotRepository.findByCategoryOrderByRating(category, newPageable);
            case "distance: ASC" -> {
                if (userX == null || userY == null) {
                    throw new NoUserPointInfoException();
                }
                page = categorySpotRepository.findByCategoryOrderByDistance(category, userX, userY, newPageable);
            }
            default -> throw new NotMatchedSortException();
        }

        SpotCategoryListRes spotCategoryListRes = new SpotCategoryListRes();
        spotCategoryListRes.setTotalPages(page.getTotalPages());

        CategoryBookmark categoryBookmark = getReqUsersCategoryBookmark(reqUser, category);
        spotCategoryListRes.setCategoryData(CategoryData.fromEntity(category, categoryBookmark));

        for (CategorySpot categorySpot : page.getContent()) {
            Spot spot = categorySpot.getSpot();
            Long spotBookmarkId = getReqUsersSpotBookmarkId(spot, reqUser);
            Double distance = DistanceCalculator.getDistanceTwoPoints(userX, userY, spot.getPointX(), spot.getPointY());

            spotCategoryListRes.getSpotDataList().add(SpotData.fromEntity(spot, spotBookmarkId, distance));
        }

        return spotCategoryListRes;
    }

    public SpotCategoryListRes findSpotListInMap(Long reqUserId, Long categoryId,
                                                 Double userX, Double userY,
                                                 Double startX, Double endX, Double startY, Double endY) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        List<CategorySpot> categorySpots = categorySpotRepository.findByCategoryInScope(category, startX, endX, startY, endY);

        SpotCategoryListRes spotCategoryListRes = new SpotCategoryListRes();
        CategoryBookmark categoryBookmark = getReqUsersCategoryBookmark(reqUser, category);
        spotCategoryListRes.setCategoryData(CategoryData.fromEntity(category, categoryBookmark));

        for (CategorySpot categorySpot : categorySpots) {
            Spot spot = categorySpot.getSpot();
            Long bookmarkId = getReqUsersSpotBookmarkId(spot, reqUser);
            Double distance = DistanceCalculator.getDistanceTwoPoints(userX, userY, spot.getPointX(), spot.getPointY());

            spotCategoryListRes.getSpotDataList().add(SpotData.fromEntity(spot, bookmarkId, distance));
        }

        return spotCategoryListRes;
    }

    public SpotListRes findSpotListByBookmark(Long reqUserId,
                                              Double userX, Double userY) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        List<SpotBookmark> spotBookmarks = spotBookmarkRepository.findByUser(reqUser);
        return SpotListRes.fromEntitiesByBookmark(spotBookmarks, userX, userY);
    }


    public SpotListRes findSpotListBySearch(Long reqUserId, String word,
                                            Double userX, Double userY, Pageable pageable) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        // Redis ZSet 처리
        redisService.addIfAbsent(word);
        redisService.incrementScore(word);

        Page<Spot> page = null;
        if (word != null) {
            page = spotRepository.findByNameContaining(word, pageable);
        } else {
            page = spotRepository.findAll(pageable);
        }

        SpotListRes res = new SpotListRes();
        if (reqUser != null) {
            for (Spot spot : page.getContent()) {
                Long bookmarkId = getReqUsersSpotBookmarkId(spot, reqUser);
                Double distance = DistanceCalculator.getDistanceTwoPoints(userX, userY, spot.getPointX(), spot.getPointY());

                res.getSpotDataList().add(SpotData.fromEntity(spot, bookmarkId, distance));
            }
        } else {
            res = SpotListRes.fromEntitiesForNoAuth(page, userX, userY);
        }

        return res;
    }

    public SpotVisitListRes findSpotVisitList(Long reqUserId, Long userId, Pageable pageable) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Page<SpotVisit> page = spotVisitRepository.findByUser(user, pageable);

        List<SpotVisit> spotVisits = page.getContent();

        SpotVisitListRes spotVisitListRes = new SpotVisitListRes();
        spotVisitListRes.setCount((int) page.getTotalElements());
        spotVisitListRes.setTotalPages(page.getTotalPages());

        for (SpotVisit spotVisit : spotVisits) {
            Long bookmarkId = getReqUsersSpotBookmarkId(spotVisit.getSpot(), reqUser);
            spotVisitListRes.getSpotVisitDataList().add(SpotVisitData.fromEntity(spotVisit, bookmarkId));
        }

        return spotVisitListRes;
    }

    public SpotDetailRes findSpotDetail(Long reqUserId, Long spotId) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        List<SpotImage> SpotImages = stillCutRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        List<Review> reviews = reviewRepository.findTop3BySpot(spot);
        Long bookmarkId = getReqUsersSpotBookmarkId(spot, reqUser);

        // 요청자의 스팟 방문 정보 처리
        Long mySpotVisitId = null;
        List<SpotVisit> spotVisits = spotVisitRepository.findByUserAndSpot(reqUser, spot);
        for (SpotVisit spotVisit : spotVisits) {
            if (spotVisit.getReview() == null) {
                mySpotVisitId = spotVisit.getId();
                break;
            }
        }

        return SpotDetailRes.fromEntity(spot, SpotImages, reviews, bookmarkId, mySpotVisitId);
    }

    @Transactional(readOnly = false)
    public void saveSpot(SpotSaveReq saveReq, MultipartFile multipartFile, List<MultipartFile> multipartFiles) {
        Spot spot = saveReq.toEntity();
        spotRepository.save(spot);

        // 이미지 처리
        String mainImagePath = s3Service.uploadFile(multipartFile);
        spot.setImagePath(mainImagePath);

        for (MultipartFile file : multipartFiles) {
            String imagePath = s3Service.uploadFile(file);
            spot.getSpotImages().add(new SpotImage(imagePath, spot));
        }

        // CategorySpot 처리
        for (Long categoryId : saveReq.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
            spot.getCategorySpots().add(new CategorySpot(category, spot));
        }

        // hashtag 처리
        for (String hashtagName : saveReq.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByName(hashtagName).orElse(null);
            if (hashtag == null) {
                hashtag = new Hashtag(hashtagName);
                hashtagRepository.save(hashtag);
            }
            spot.getHashtagSpots().add(new HashtagSpot(hashtag, spot));
        }

        // 알림 처리
        processAlarmInSavingSpot(spot);
    }

    @Transactional(readOnly = false)
    public void removeSpot(Long spotId) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        // 이미지 처리
        if (spot.getImagePath() != null) {
            s3Service.deleteFile(spot.getName());
        }

        for (SpotImage spotImage : spot.getSpotImages()) {
            s3Service.deleteFile(spotImage.getImagePath());
        }

        spotRepository.delete(spot);
    }

    @ExpHandlingTargetMethod
    @QuestHandlingTargetMethod
    @Transactional(readOnly = false)
    public void visitSpot(Long reqUserId, Long spotId, Double userX, Double userY) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        double distance = DistanceCalculator.getDistanceTwoPoints(userX, userY, spot.getPointX(), spot.getPointY());
        if (distance > 20.0) {
            throw new SpotVisitDistanceException();
        }

        spotVisitRepository.save(new SpotVisit(spot, reqUser));

        // 알림 처리
        processAlarmInVisitSpot(reqUser, spot);
    }

    private void processAlarmInSavingSpot(Spot spot) {
        String title = "관심있는 카테고리에 새로운 스팟이 등록되었습니다 !!";
        String body = "[" + spot.getName() + "]" + " 스팟을 확인해보세요 !!";
        String path = "/spot/" + spot.getId();

        Set<User> targets = new HashSet<>();
        List<CategorySpot> categorySpots = spot.getCategorySpots();
        for (CategorySpot categorySpot : categorySpots) {
            List<CategoryBookmark> categoryBookmarks = categoryBookmarkRepository.findByCategory(categorySpot.getCategory());
            for (CategoryBookmark categoryBookmark : categoryBookmarks) {
                targets.add(categoryBookmark.getUser());
            }
        }

        alarmRepository.saveAll(Alarm.createAlarms(title, body, path, targets.stream().toList()));
    }

    private void processAlarmInVisitSpot(User user, Spot spot) {
        alarmRepository.saveAll(Alarm.createAlarms(
                "방문한 스팟에 리뷰를 남겨보세요 !!",
                spot.getName() + "에 " + user.getNickname() + "님의 소중한 리뷰를 남겨주세요.",
                "/spot/" + spot.getId(),
                List.of(user))
        );
    }

    private Long getReqUsersSpotBookmarkId(Spot spot, User user) {
        if (user == null) {
            return null;
        }
        Optional<SpotBookmark> findBookmark = spotBookmarkRepository.findBySpotAndUser(spot, user);
        return findBookmark.map(SpotBookmark::getId).orElse(null);
    }

    private CategoryBookmark getReqUsersCategoryBookmark(User reqUser, Category category) {
        if (reqUser == null) {
            return null;
        } else {
            Optional<CategoryBookmark> categoryBookmark = categoryBookmarkRepository.findByCategoryAndUser(category, reqUser);
            return categoryBookmark.orElse(null);
        }
    }

}
