package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.review.domain.dto.CommentSaveReq;
import site.dittotrip.ditto_trip.review.repository.ReviewCommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;

    /**
     * 등록순, 최신순 기획 변경될 수 있음
     * 자식 댓글 날짜 정렬안되면 수정하기
     * 삭제된 댓글의 자식댓글 보여줄 것인가 등등 기획 확인 후 수정
     */
//    public CommentListRes findCommentList(Long reviewId, User user) {
//        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);
//
//        List<ReviewComment> parentReviewComments = commentRepository.findByReviewAndParentCommentIsNullOrderByCreatedDateTimeAsc(review);
//
//        List<CommentData> commentDataList = new ArrayList<>();
//        for (ReviewComment parentReviewComment : parentReviewComments) {
//            CommentData commentData = CommentData.parentFromEntity(parentReviewComment);
//            commentData.setIsMine(getIsMine(user));
//            commentDataList.add(commentData);
//        }
//
//        return new CommentListRes(commentDataList);
//    }

    @Transactional(readOnly = false)
    public void saveComment(Long reviewId, Long parentCommentId, User user,
                            CommentSaveReq commentSaveReq) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);

        ReviewComment parentReviewComment = null;
        if (parentCommentId != null) {
            parentReviewComment = reviewCommentRepository.findById(parentCommentId).orElseThrow(NoSuchElementException::new);
        }

        ReviewComment reviewComment = new ReviewComment(commentSaveReq.getBody(), user, review, parentReviewComment);
        reviewCommentRepository.save(reviewComment);
    }

    @Transactional(readOnly = false)
    public void modifyComment(Long commentId, User user, CommentSaveReq commentSaveReq) {
        ReviewComment reviewComment = reviewCommentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);

        if (!reviewComment.getUser().equals(user)) {
            // throw new NoAuthorityException;
        }

        reviewComment.setBody(commentSaveReq.getBody());
    }

    @Transactional(readOnly = false)
    public void removeComment(Long commentId, User user) {
        ReviewComment reviewComment = reviewCommentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);

        if (!reviewComment.getUser().equals(user)) {
            // throw new NoAuthorityException;
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
