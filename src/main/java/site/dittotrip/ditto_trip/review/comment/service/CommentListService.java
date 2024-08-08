package site.dittotrip.ditto_trip.review.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.comment.domain.Comment;
import site.dittotrip.ditto_trip.review.comment.domain.dto.list.CommentListRes;
import site.dittotrip.ditto_trip.review.comment.repository.CommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentListService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    /**
     * 등록순, 최신순
     */
    public CommentListRes findCommentList(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);

        List<Comment> parentComments = commentRepository.findByReviewAndParentCommentIsNullOrderByCreatedDateTimeAsc(review);



    }

}
