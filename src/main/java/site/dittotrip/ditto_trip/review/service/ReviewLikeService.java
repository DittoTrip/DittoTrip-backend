package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.ReviewLike;
import site.dittotrip.ditto_trip.review.exception.ExistingReviewLikeException;
import site.dittotrip.ditto_trip.review.repository.ReviewLikeRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

/**
 * ReviewService 합치기
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;

    public void saveReviewLike(Review review, User user) {
        reviewLikeRepository.findByReviewAndUser(review, user).ifPresent(m -> {
            throw new ExistingReviewLikeException();
        });

        ReviewLike reviewLike = new ReviewLike(review, user);
        reviewLikeRepository.save(reviewLike);
        review.setLikes(review.getLikes() + 1);
    }

    public void removeReviewLike(Review review, User user) {
        ReviewLike reviewLike = reviewLikeRepository.findByReviewAndUser(review, user).orElseThrow(NoSuchElementException::new);
        reviewLikeRepository.delete(reviewLike);
        review.setLikes(review.getLikes() - 1);
    }

}
