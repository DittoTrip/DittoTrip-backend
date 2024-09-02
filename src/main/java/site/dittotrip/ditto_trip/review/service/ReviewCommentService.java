package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.review.domain.dto.CommentSaveReq;
import site.dittotrip.ditto_trip.review.exception.DoubleChildReviewCommentException;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.review.exception.NotMatchedRelationException;
import site.dittotrip.ditto_trip.review.repository.ReviewCommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewCommentService {

    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;

    @Transactional(readOnly = false)
    public void saveComment(Long reviewId, Long parentCommentId, User user,
                            CommentSaveReq commentSaveReq) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);

        ReviewComment parentReviewComment = null;
        if (parentCommentId != null) {
            parentReviewComment = reviewCommentRepository.findById(parentCommentId).orElseThrow(NoSuchElementException::new);
            if (parentReviewComment.getParentReviewComment() != null) {
                throw new DoubleChildReviewCommentException();
            }

            if (!parentReviewComment.getReview().equals(review)) {
                throw new NotMatchedRelationException();
            }
        }

        ReviewComment reviewComment = new ReviewComment(commentSaveReq.getBody(), user, review, parentReviewComment);
        reviewCommentRepository.save(reviewComment);
    }

    @Transactional(readOnly = false)
    public void modifyComment(Long commentId, User user, CommentSaveReq commentSaveReq) {
        ReviewComment reviewComment = reviewCommentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);

        if (reviewComment.getUser().getId() != user.getId()) {
             throw new NoAuthorityException();
        }

        reviewComment.setBody(commentSaveReq.getBody());
    }

    @Transactional(readOnly = false)
    public void removeComment(Long commentId, User user) {
        ReviewComment reviewComment = reviewCommentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);

        if (reviewComment.getUser().getId() != user.getId()) {
             throw new NoAuthorityException();
        }

        reviewCommentRepository.delete(reviewComment);
    }


    private Boolean getIsMine(User user) {
        if (user == null) {
            return Boolean.FALSE;
        }

        Optional<ReviewComment> findComment = reviewCommentRepository.findByUser(user);
        if (findComment.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.TRUE;
        }
    }

}
