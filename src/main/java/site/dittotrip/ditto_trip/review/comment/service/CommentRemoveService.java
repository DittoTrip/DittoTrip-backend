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
public class CommentRemoveService {

    private final CommentRepository commentRepository;

    public void removeComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(NoSuchElementException::new);

        if (!comment.getUser().equals(user)) {
            // throw new NoAuthorityException;
        }

        commentRepository.delete(comment);
    }

}
