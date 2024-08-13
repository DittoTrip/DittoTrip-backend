package site.dittotrip.ditto_trip.review.reviewlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.reviewlike.domain.ReviewLike;
import site.dittotrip.ditto_trip.review.reviewlike.exception.ExistingReviewLikeException;
import site.dittotrip.ditto_trip.review.reviewlike.repository.ReviewLikeRepository;
import site.dittotrip.ditto_trip.user.domain.User;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ReviewLikeSaveService {

    private final ReviewLikeRepository reviewLikeRepository;

    public void saveReviewLike(Review review, User user) {
        reviewLikeRepository.findByReviewAndUser(review, user).ifPresent(m -> {
            throw new ExistingReviewLikeException();
        });

        ReviewLike reviewLike = new ReviewLike(review, user);
        reviewLikeRepository.save(reviewLike);
        review.setLikes(review.getLikes() + 1);
    }

}
