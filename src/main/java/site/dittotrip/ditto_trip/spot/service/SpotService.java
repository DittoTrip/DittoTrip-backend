package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryData;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.spot.domain.*;
import site.dittotrip.ditto_trip.spot.domain.dto.*;
import site.dittotrip.ditto_trip.spot.repository.*;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryBookmarkRepository categoryBookmarkRepository;
    private final CategorySpotRepository categorySpotRepository;
    private final StillCutRepository stillCutRepository;
    private final ReviewRepository reviewRepository;
    private final SpotBookmarkRepository spotBookmarkRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final UserRepository userRepository;


    public SpotCategoryListRes findSpotListInCategory(User user, Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        Page<CategorySpot> page = categorySpotRepository.findByCategory(category, pageable);

        SpotCategoryListRes spotCategoryListRes = new SpotCategoryListRes();
        spotCategoryListRes.setTotalPages(page.getTotalPages());

        CategoryBookmark categoryBookmark = getReqUsersCategoryBookmark(user, category);
        spotCategoryListRes.setCategoryData(CategoryData.fromEntity(category, categoryBookmark));

        for (CategorySpot categorySpot : page.getContent()) {
            Spot spot = categorySpot.getSpot();
            Long spotBookmarkId = spotBookmarkId(spot, user);
            spotCategoryListRes.getSpotDataList().add(SpotData.fromEntity(spot, spotBookmarkId));
        }

        return spotCategoryListRes;
    }

    public SpotCategoryListRes findSpotListInMap(Long categoryId, User user,
                                                 Double startX, Double endX, Double startY, Double endY) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        List<CategorySpot> categorySpots = categorySpotRepository.findByCategoryInScope(category, startX, endX, startY, endY);

        SpotCategoryListRes spotCategoryListRes = new SpotCategoryListRes();
        CategoryBookmark categoryBookmark = getReqUsersCategoryBookmark(user, category);
        spotCategoryListRes.setCategoryData(CategoryData.fromEntity(category, categoryBookmark));

        for (CategorySpot categorySpot : categorySpots) {
            Spot spot = categorySpot.getSpot();
            Long bookmarkId = spotBookmarkId(spot, user);
            spotCategoryListRes.getSpotDataList().add(SpotData.fromEntity(spot, bookmarkId));
        }

        return spotCategoryListRes;
    }

    public SpotListRes findSpotListByBookmark(User user) {
        List<SpotBookmark> spotBookmarks = spotBookmarkRepository.findByUser(user);
        return SpotListRes.fromEntitiesByBookmark(spotBookmarks);
    }


    public SpotListRes findSpotListBySearch(User user, String word, Pageable pageable) {
        List<Spot> spots = spotRepository.findByNameContaining(word, pageable);

        SpotListRes spotListRes = new SpotListRes();
        spotListRes.setSpotCount(spots.size());
        for (Spot spot : spots) {
            Long bookmarkId = spotBookmarkId(spot, user);
            spotListRes.getSpotDataList().add(SpotData.fromEntity(spot, bookmarkId));
        }

        return spotListRes;
    }

    public SpotVisitListRes findSpotVisitList(Long userId, User reqUser, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Page<SpotVisit> page = spotVisitRepository.findByUser(user, pageable);

        List<SpotVisit> spotVisits = page.getContent();

        SpotVisitListRes spotVisitListRes = new SpotVisitListRes();
        spotVisitListRes.setCount((int) page.getTotalElements());
        spotVisitListRes.setTotalPages(page.getTotalPages());

        for (SpotVisit spotVisit : spotVisits) {
            Long bookmarkId = spotBookmarkId(spotVisit.getSpot(), reqUser);
            spotVisitListRes.getSpotVisitDataList().add(SpotVisitData.fromEntity(spotVisit, bookmarkId));
        }

        return spotVisitListRes;
    }

    public SpotDetailRes findSpotDetail(Long spotId, User user) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        List<SpotImage> SpotImages = stillCutRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        List<Review> reviews = reviewRepository.findTop3BySpot(spot);
        Long bookmarkId = spotBookmarkId(spot, user);

        return SpotDetailRes.fromEntity(spot, SpotImages, reviews, bookmarkId);
    }

    private Long spotBookmarkId(Spot spot, User user) {
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
