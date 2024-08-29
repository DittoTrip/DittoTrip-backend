package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.review.domain.dto.*;
import site.dittotrip.ditto_trip.review.exception.AlreadyWriteReviewException;
import site.dittotrip.ditto_trip.review.exception.ReviewWritePeriodOverException;
import site.dittotrip.ditto_trip.review.repository.ReviewCommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.review.exception.TooManyImagesException;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.review.domain.ReviewLike;
import site.dittotrip.ditto_trip.review.repository.ReviewLikeRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotVisitRepository;
import site.dittotrip.ditto_trip.user.domain.User;

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

    private final SpotRepository spotRepository;
    private final SpotVisitRepository spotVisitRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    private final int REVIEW_WRITE_PERIOD = 604800;

    public ReviewListRes findReviewList(Long spotId, User user, Pageable pageable) {
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
            Boolean isMine = getIsMine(review, user);
            Boolean myLike = getMyLike(review, user);

            reviewListRes.getReviewDataList().add(ReviewData.fromEntity(review, isMine, myLike, commentsCount));
        }

        return reviewListRes;
    }

    public ReviewDetailRes findReviewDetail(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
        Spot spot = review.getSpotVisit().getSpot();

        ReviewDetailRes reviewDetailRes = new ReviewDetailRes();
        reviewDetailRes.setSpotName(spot.getSpotName());

        int commentsCount = reviewCommentRepository.countByReview(review).intValue();
        Boolean isMine = getIsMine(review, user);
        Boolean myLike = getMyLike(review, user);
        reviewDetailRes.setReviewData(ReviewData.fromEntity(review, isMine, myLike, commentsCount));

        List<ReviewComment> parentComments = reviewCommentRepository.findParentCommentsByReview(review);
        List<ReviewCommentData> commentDataList = new ArrayList<>();
        for (ReviewComment parentComment : parentComments) {
            commentDataList.add(ReviewCommentData.parentFromEntity(parentComment, user));
        }
        reviewDetailRes.setReviewCommentDataList(commentDataList);

        return reviewDetailRes;
    }

    @Transactional(readOnly = false)
    public void saveReview(Long spotVisitId, User user, ReviewSaveReq reviewSaveReq, List<MultipartFile> multipartFiles) {
        SpotVisit spotVisit = spotVisitRepository.findById(spotVisitId).orElseThrow(NoSuchElementException::new);

        reviewRepository.findBySpotVisit(spotVisit).ifPresent(m -> {
            throw new AlreadyWriteReviewException();
        });

        Duration duration = Duration.between(spotVisit.getCreatedDateTime(), LocalDateTime.now());
        if (duration.getSeconds() > REVIEW_WRITE_PERIOD) {
            throw new ReviewWritePeriodOverException();
        }

        if (multipartFiles.size() > 10) {
            throw new TooManyImagesException();
        }

        Review review = reviewSaveReq.toEntity(user, spotVisit);

        // image 처리

        modifySpotRating(spotVisit.getSpot(), review.getRating(), null);

        reviewRepository.save(review);
    }

    @Transactional(readOnly = false)
    public void modifyReview(Long reviewId, User user, ReviewModifyReq modifyReq, List<MultipartFile> multipartFiles) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
        Float oldRating = review.getRating();

        if (review.getUser().getId() != user.getId()) {
             throw new NoAuthorityException();
        }

        if (review.getReviewImages().size() + multipartFiles.size() - modifyReq.getRemovedImageIds().size() > 10) {
            throw new TooManyImagesException();
        }

        modifyReq.modifyEntity(review);

        // image 처리

        modifySpotRating(review.getSpotVisit().getSpot(), review.getRating(), oldRating);
    }

    @Transactional(readOnly = false)
    public void removeReview(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);

        if (review.getUser().getId() != user.getId()) {
             throw new NoAuthorityException();
        }

        // image 처리

        modifySpotRating(review.getSpotVisit().getSpot(), null, review.getRating());
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

    private void modifySpotRating(Spot spot, Float add, Float sub) {
        int reviewCount = spot.getReviewCount();
        float ratingSum = spot.getRating() * reviewCount;

        if (add != null) {
            reviewCount++;
            ratingSum += add;
        }

        if (sub != null) {
            reviewCount--;
            ratingSum -= sub;
        }

        Float newRating = ratingSum / reviewCount;

        spot.setReviewCount(reviewCount);
        spot.setRating(newRating);
    }

}
