package site.dittotrip.ditto_trip.review.reviewlike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.reviewlike.domain.ReviewLike;
import site.dittotrip.ditto_trip.review.reviewlike.repository.ReviewLikeRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ReviewLikeRemoveService {

    private final ReviewLikeRepository reviewLikeRepository;

    public void removeReviewLike(Review review, User user) {
        ReviewLike reviewLike = reviewLikeRepository.findByReviewAndUser(review, user).orElseThrow(NoSuchElementException::new);
        reviewLikeRepository.delete(reviewLike);
        review.setLikes(review.getLikes() - 1);
    }

}
