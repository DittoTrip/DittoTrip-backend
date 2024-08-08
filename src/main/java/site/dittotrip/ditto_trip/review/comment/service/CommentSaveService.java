package site.dittotrip.ditto_trip.review.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.comment.repository.CommentRepository;
import site.dittotrip.ditto_trip.user.domain.User;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CommentSaveService {

    private final CommentRepository commentRepository;

    /**
     * 리뷰 id, 부모 댓글 id, 유저 id
     */
    public void saveComment(Long reviewId, Long parentCommentId, User user) {

    }

}
