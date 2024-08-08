package site.dittotrip.ditto_trip.review.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.comment.domain.Comment;
import site.dittotrip.ditto_trip.review.comment.domain.dto.save.CommentSaveReq;
import site.dittotrip.ditto_trip.review.comment.repository.CommentRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CommentModifyService {

    private final CommentRepository commentRepository;

    public void modifyComment(Long commentId, User user, CommentSaveReq commentSaveReq) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);

        if (!comment.getUser().equals(user)) {
            // throw new NoAuthorityException;
        }

        comment.setBody(commentSaveReq.getBody());
    }

}
