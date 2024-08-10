package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.comment.repository.CommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewData;
import site.dittotrip.ditto_trip.review.domain.dto.list.ReviewListRes;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.review.reviewlike.domain.ReviewLike;
import site.dittotrip.ditto_trip.review.reviewlike.repository.ReviewLikeRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewListService {

    private final SpotRepository spotRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    /**
     * 최신순, 인기순
     */
    public ReviewListRes findReviewList(Long spotId, User user) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);
        List<Review> reviews = reviewRepository.findBySpotOrderByCreatedDateTimeDesc(spot);

        Integer reviewCount = reviewRepository.countBySpot(spot).intValue();

        Float totalRating = 0.0f;

        List<ReviewData> reviewDataList = new ArrayList<>();
        for (Review review : reviews) {
            Long likes = reviewLikeRepository.countByReview(review);
            Long commentsCount = commentRepository.countByReview(review);
            Boolean isMine = putIsMine(user);
            Boolean myLike = putMyLike(review, user);

            reviewDataList.add(ReviewData.fromEntity(review, likes.intValue(), isMine, myLike, commentsCount.intValue()));
            totalRating += review.getRating();
        }

        Float avgRating = calculateAvgOfRating(totalRating, reviewCount);

        return new ReviewListRes(reviewCount, avgRating, reviewDataList);
    }

    /**
     * @return 0.0f, 0.5f, 1.0f, 1.5f, 2.0f, ... 4.5f, 5.0f
     */
    private Float calculateAvgOfRating(Float totalRating, Integer reviewCount) {
        float avg = totalRating / reviewCount.floatValue();
        return Math.round(avg * 20) / 20.0f;
    }

    private Boolean putIsMine(User user) {
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

    private Boolean putMyLike(Review review, User user) {
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

}
