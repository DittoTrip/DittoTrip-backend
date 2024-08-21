package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.review.repository.CommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewData;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewListRes;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewModifyReq;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewSaveReq;
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
    private final CommentRepository commentRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    private static final int PAGE_SIZE = 10;

    public ReviewListRes findReviewList(Long spotId, User user, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);

        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);
        Page<Review> reviewsPage = reviewRepository.findBySpot(spot, pageRequest);
        List<Review> reviews = reviewsPage.getContent();


        ReviewListRes reviewListRes = ReviewListRes.builder()
                .reviewsCount((int) reviewsPage.getTotalElements())
                .rating(spot.getRating())
                .totalPage(reviewsPage.getTotalPages())
                .build();

        for (Review review : reviews) {
            Integer commentsCount = commentRepository.countByReview(review).intValue();
            Boolean isMine = getIsMine(user);
            Boolean myLike = getMyLike(review, user);

            reviewListRes.getReviewDataList().add(ReviewData.fromEntity(review, isMine, myLike, commentsCount));
        }

        return reviewListRes;
    }

    @Transactional(readOnly = false)
    public void saveReview(Long spotVisitId, User user, ReviewSaveReq reviewSaveReq, List<MultipartFile> multipartFiles) {
        SpotVisit spotVisit = spotVisitRepository.findById(spotVisitId).orElseThrow(NoSuchElementException::new);

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

        if (!review.getUser().equals(user)) {
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

        if (!review.getUser().equals(user)) {
             throw new NoAuthorityException();
        }

        // image 처리

        modifySpotRating(review.getSpotVisit().getSpot(), null, review.getRating());
        reviewRepository.delete(review);
    }

    private Boolean getIsMine(User user) {
        if (user == null) {
            return Boolean.FALSE;
        }

        Optional<Review> findReview = reviewRepository.findByUser(user);
        if (findReview.isPresent()) {
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
