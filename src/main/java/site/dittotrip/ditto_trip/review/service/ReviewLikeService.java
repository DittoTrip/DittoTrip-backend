package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.ReviewLike;
import site.dittotrip.ditto_trip.review.exception.ExistingReviewLikeException;
import site.dittotrip.ditto_trip.review.repository.ReviewLikeRepository;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * ReviewService 합치기
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewLikeService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public Boolean getReviewLike(Long reqUserId, Long reviewId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
        Optional<ReviewLike> optionalLike = reviewLikeRepository.findByReviewAndUser(review, reqUser);
        return optionalLike.isPresent();
    }

    @Transactional(readOnly = false)
    public void saveReviewLike(Long reqUserId, Long reviewId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
        reviewLikeRepository.findByReviewAndUser(review, reqUser).ifPresent(m -> {
            throw new ExistingReviewLikeException();
        });

        ReviewLike reviewLike = new ReviewLike(review, reqUser);
        reviewLikeRepository.save(reviewLike);
        review.setLikes(review.getLikes() + 1);
    }

    @Transactional(readOnly = false)
    public void removeReviewLike(Long reqUserId, Long reviewId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
        ReviewLike reviewLike = reviewLikeRepository.findByReviewAndUser(review, reqUser).orElseThrow(NoSuchElementException::new);
        reviewLikeRepository.delete(reviewLike);
        review.setLikes(review.getLikes() - 1);
    }

}
