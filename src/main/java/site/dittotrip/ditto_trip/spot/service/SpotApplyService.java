package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpotApply;
import site.dittotrip.ditto_trip.hashtag.repository.HashtagRepository;
import site.dittotrip.ditto_trip.exception.common.NoAuthorityException;
import site.dittotrip.ditto_trip.spot.domain.*;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplyDetailRes;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplyListRes;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplyMiniListRes;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplySaveReq;
import site.dittotrip.ditto_trip.spot.domain.enums.SpotApplyStatus;
import site.dittotrip.ditto_trip.spot.exception.AlreadyHandledSpotApplyException;
import site.dittotrip.ditto_trip.spot.repository.SpotApplyRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.S3Service;
import site.dittotrip.ditto_trip.utils.TranslationService;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotApplyService {

    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final SpotApplyRepository spotApplyRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryBookmarkRepository categoryBookmarkRepository;
    private final HashtagRepository hashtagRepository;
    private final AlarmRepository alarmRepository;
    private final S3Service s3Service;
    private final TranslationService translationService;

    public SpotApplyListRes findSpotApplyList(String word, Pageable pageable) {
        Page<SpotApply> page;
        if (word != null) {
            page = spotApplyRepository.findByNameContaining(word, pageable);
        } else {
            page = spotApplyRepository.findAll(pageable);
        }

        return SpotApplyListRes.fromEntities(page);
    }

    public SpotApplyMiniListRes findMySpotApplyList(Long reqUserId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        List<SpotApply> spotApplies = spotApplyRepository.findByUser(reqUser);
        return SpotApplyMiniListRes.fromEntities(spotApplies);
    }

    public SpotApplyDetailRes findSpotApplyDetail(Long reqUserId, Long spotApplyId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        SpotApply spotApply = spotApplyRepository.findById(spotApplyId).orElseThrow(NoSuchElementException::new);

        // 관리자 관한 조건 추가 필요
        if (!reqUser.equals(spotApply.getUser())) {
            throw new NoAuthorityException();
        }

        return SpotApplyDetailRes.fromEntity(spotApply);
    }

    @Transactional(readOnly = false)
    public void saveSpotApply(Long reqUserId, SpotApplySaveReq saveReq, MultipartFile multipartFile, List<MultipartFile> multipartFiles) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        SpotApply spotApply = saveReq.toEntity(reqUser);
        spotApplyRepository.save(spotApply);

        // image 처리
        String mainImagePath = s3Service.uploadFile(multipartFile);
        spotApply.setImagePath(mainImagePath);

        for (MultipartFile file : multipartFiles) {
            String imagePath = s3Service.uploadFile(file);
            spotApply.getSpotApplyImages().add(new SpotApplyImage(imagePath, spotApply));
        }

        // categorySpotApply 처리
        for (Long categoryId : saveReq.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
            spotApply.getCategorySpotApplies().add(new CategorySpotApply(category, spotApply));
        }

        // hashtag 처리
        for (String hashtagName : saveReq.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByName(hashtagName).orElse(null);
            if (hashtag == null) {
                hashtag = new Hashtag(hashtagName);
                hashtagRepository.save(hashtag);
            }
            spotApply.getHashtagSpotApplies().add(new HashtagSpotApply(hashtag, spotApply));
        }

    }

    @Transactional(readOnly = false)
    public void removeSpotApply(Long spotApplyId) {
        SpotApply spotApply = spotApplyRepository.findById(spotApplyId).orElseThrow(NoSuchElementException::new);
        s3Service.deleteFile(spotApply.getImagePath());
        for (SpotApplyImage spotApplyImage : spotApply.getSpotApplyImages()) {
            s3Service.deleteFile(spotApplyImage.getImagePath());
        }

        spotApplyRepository.delete(spotApply);
    }

    @Transactional(readOnly = false)
    public void handleSpotApply(Long spotApplyId, Boolean isApproval) {
        SpotApply spotApply = spotApplyRepository.findById(spotApplyId).orElseThrow(NoSuchElementException::new);

        if (!spotApply.getSpotApplyStatus().equals(SpotApplyStatus.PENDING)) {
            throw new AlreadyHandledSpotApplyException();
        }

        if (isApproval) {
            Spot spot = Spot.fromSpotApply(spotApply);

            String[] textList = new String[1];
            textList[0] = spot.getName();
            String nameEN = translationService.translateText(textList).getTranslations().get(0).getText();
            spot.setNameEN(nameEN);

            spotRepository.save(spot);
            spotApply.setSpotApplyStatus(SpotApplyStatus.APPROVED);
            processAlarmInHandlingSpotApply(spotApply, spot);
        } else {
            spotApply.setSpotApplyStatus(SpotApplyStatus.REJECTED);
        }
    }

    private void processAlarmInHandlingSpotApply(SpotApply spotApply, Spot spot) {
        String title1 = "스팟 신청이 처리되었습니다 !!";
        String body1 = "[" + spot.getName() + "]" + " 스팟을 확인해보세요 !!";
        String path1 = "/spot/" + spot.getId();

        User spotApplier = spotApply.getUser();
        List<User> target1 = new ArrayList<>(List.of(spotApplier));
        alarmRepository.saveAll(Alarm.createAlarms(title1, body1, path1, target1));

        String title2 = "관심있는 카테고리에 새로운 스팟이 등록되었습니다 !!";
        String body2 = "[" + spot.getName() + "]" + " 스팟을 확인해보세요 !!";
        String path2 = "/spot/" + spot.getId();
        Set<User> target2 = new HashSet<>();
        target2.add(spotApplier);
        List<CategorySpot> categorySpots = spot.getCategorySpots();
        for (CategorySpot categorySpot : categorySpots) {
            List<CategoryBookmark> categoryBookmarks = categoryBookmarkRepository.findByCategory(categorySpot.getCategory());
            for (CategoryBookmark categoryBookmark : categoryBookmarks) {
                target2.add(categoryBookmark.getUser());
            }
        }
        target2.remove(spotApplier);

        alarmRepository.saveAll(Alarm.createAlarms(title2, body2, path2, target2.stream().toList()));
    }


}
