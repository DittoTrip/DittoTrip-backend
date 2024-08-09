package site.dittotrip.ditto_trip.review.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.comment.domain.Comment;
import site.dittotrip.ditto_trip.review.comment.domain.dto.list.CommentListRes;
import site.dittotrip.ditto_trip.review.comment.repository.CommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentListService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    /**
     * 등록순, 최신순 기획 변경될 수 있음
     * 자식 댓글 날짜 정렬안되면 수정하기
     * 삭제된 댓글의 자식댓글 보여줄 것인가 등등 기획 확인 후 수정
     */
    public CommentListRes findCommentList(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);

        List<Comment> parentComments = commentRepository.findByReviewAndParentCommentIsNullOrderByCreatedDateTimeAsc(review);

        return CommentListRes.fromEntity(parentComments);
    }

}
