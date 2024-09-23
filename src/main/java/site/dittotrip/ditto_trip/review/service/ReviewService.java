package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.quest.aop.ExpHandlingTargetMethod;
import site.dittotrip.ditto_trip.quest.aop.QuestHandlingTargetMethod;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.review.domain.ReviewImage;
import site.dittotrip.ditto_trip.review.domain.dto.*;
import site.dittotrip.ditto_trip.review.exception.AlreadyWriteReviewException;
import site.dittotrip.ditto_trip.review.exception.ReviewWritePeriodOverException;
import site.dittotrip.ditto_trip.review.repository.ReviewCommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.exception.common.NoAuthorityException;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.review.domain.ReviewLike;
import site.dittotrip.ditto_trip.review.repository.ReviewLikeRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotVisitRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.S3Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 이미지 처리 x
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final AlarmRepository alarmRepository;
    private final S3Service s3Service;

    private final int REVIEW_WRITE_PERIOD = 604800; // 1 week

    public ReviewListRes findReviewList(Long reqUserId, Long spotId, Pageable pageable) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);
        Page<Review> reviewsPage = reviewRepository.findBySpot(spot, pageable);
        List<Review> reviews = reviewsPage.getContent();

        ReviewListRes reviewListRes = ReviewListRes.builder()
                .reviewsCount((int) reviewsPage.getTotalElements())
                .rating(spot.getRating())
                .totalPage(reviewsPage.getTotalPages())
                .build();

        for (Review review : reviews) {
            Integer commentsCount = reviewCommentRepository.countByReview(review).intValue();
            Boolean isMine = getIsMine(review, reqUser);
            Boolean myLike = getMyLike(review, reqUser);

            reviewListRes.getReviewDataList().add(ReviewData.fromEntity(review, isMine, myLike, commentsCount));
        }

        return reviewListRes;
    }

    public ReviewDetailRes findReviewDetail(Long reqUserId, Long reviewId) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }


        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
        Spot spot = review.getSpotVisit().getSpot();

        ReviewDetailRes reviewDetailRes = new ReviewDetailRes();
        reviewDetailRes.setSpotName(spot.getName());

        int commentsCount = reviewCommentRepository.countByReview(review).intValue();
        Boolean isMine = getIsMine(review, reqUser);
        Boolean myLike = getMyLike(review, reqUser);
        reviewDetailRes.setReviewData(ReviewData.fromEntity(review, isMine, myLike, commentsCount));

        List<ReviewComment> parentComments = reviewCommentRepository.findParentCommentsByReview(review);
        List<ReviewCommentData> commentDataList = new ArrayList<>();
        for (ReviewComment parentComment : parentComments) {
            commentDataList.add(ReviewCommentData.parentFromEntity(parentComment, reqUser));
        }
        reviewDetailRes.setCommentDataList(commentDataList);

        return reviewDetailRes;
    }

    @QuestHandlingTargetMethod
    @ExpHandlingTargetMethod
    @Transactional(readOnly = false)
    public void saveReview(Long reqUserId, Long spotVisitId, ReviewSaveReq reviewSaveReq, List<MultipartFile> multipartFiles) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        SpotVisit spotVisit = spotVisitRepository.findById(spotVisitId).orElseThrow(NoSuchElementException::new);

        reviewRepository.findBySpotVisit(spotVisit).ifPresent(m -> {
            throw new AlreadyWriteReviewException();
        });

        Duration duration = Duration.between(spotVisit.getCreatedDateTime(), LocalDateTime.now());
        if (duration.getSeconds() > REVIEW_WRITE_PERIOD) {
            throw new ReviewWritePeriodOverException();
        }

        Review review = reviewSaveReq.toEntity(reqUser, spotVisit);
        reviewRepository.save(review);

        // image 처리
        for (MultipartFile multipartFile : multipartFiles) {
            String imagePath = s3Service.uploadFile(multipartFile);
            review.getReviewImages().add(new ReviewImage(imagePath, review));
        }

        // spot field 업데이트
        Review.modifySpotByReview(spotVisit.getSpot(), review.getRating(), null);

        // alarm 처리
        processAlarmInSaveReview(review);
    }

    @Transactional(readOnly = false)
    public void modifyReview(Long reqUserId, Long reviewId, ReviewSaveReq saveReq, List<MultipartFile> multipartFiles) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
        Float oldRating = review.getRating();

        if (review.getUser().getId() != reqUser.getId()) {
             throw new NoAuthorityException();
        }

        saveReq.modifyEntity(review);

        // image 처리
        List<ReviewImage> reviewImages = review.getReviewImages();
        for (ReviewImage image : reviewImages) {
            s3Service.deleteFile(image.getImagePath());
        }
        reviewImages.clear();

        for (MultipartFile multipartFile : multipartFiles) {
            String imagePath = s3Service.uploadFile(multipartFile);
            reviewImages.add(new ReviewImage(imagePath, review));
        }

        // spot field 업데이트
        Review.modifySpotByReview(review.getSpotVisit().getSpot(), review.getRating(), oldRating);
    }

    @Transactional(readOnly = false)
    public void removeReview(Long reqUserId, Long reviewId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
        if (review.getUser().getId() != reqUser.getId()) {
             throw new NoAuthorityException();
        }

        // image 처리
        for (ReviewImage reviewImage : review.getReviewImages()) {
            s3Service.deleteFile(reviewImage.getImagePath());
        }

        // spot field 업데이트
        Review.modifySpotByReview(review.getSpotVisit().getSpot(), null, review.getRating());

        reviewRepository.delete(review);
    }

    private Boolean getIsMine(Review review, User user) {
        if (user == null) {
            return Boolean.FALSE;
        }

        if (review.getUser().getId() == user.getId()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private Boolean getMyLike(Review review, User user) {
        if (user == null) {
            return Boolean.FALSE;
        }

        Optional<ReviewLike> findReviewLike = reviewLikeRepository.findByReviewAndUser(review, user);
        if (findReviewLike.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private void processAlarmInSaveReview(Review review) {
        String title = "새로운 리뷰가 있어요 !!";
        String body = review.getUser() + " 님이 리뷰를 작성했어요 !!";
        String path = "/review/" + review.getId();
        List<User> targets = new ArrayList<>();
        List<Follow> followeds = review.getUser().getFolloweds();
        for (Follow followed : followeds) {
            targets.add(followed.getFollowingUser());
        }

        alarmRepository.saveAll(Alarm.createAlarms(title, body, path, targets));
    }

}
