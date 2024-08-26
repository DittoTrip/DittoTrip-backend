package site.dittotrip.ditto_trip.ditto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoCommentSaveReq;
import site.dittotrip.ditto_trip.ditto.repository.DittoCommentRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.review.exception.NotMatchedRelationException;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DittoCommentService {

    private final DittoRepository dittoRepository;
    private final DittoCommentRepository dittoCommentRepository;

    public void saveDittoComment(Long dittoId, Long parentCommentId, User user,
                                 DittoCommentSaveReq saveReq) {
        Ditto ditto = dittoRepository.findById(dittoId).orElseThrow(NoSuchElementException::new);

        DittoComment parentComment = null;
        if (parentCommentId != null) {
            parentComment = dittoCommentRepository.findById(parentCommentId).orElseThrow(NoSuchElementException::new);
            if (!parentComment.getDitto().equals(ditto)) {
                throw new NotMatchedRelationException();
            }
        }

        DittoComment dittoComment = saveReq.toEntity(ditto, user, parentComment);
        dittoCommentRepository.save(dittoComment);
    }

    public void modifyDittoComment(Long dittoCommentId, User user, DittoCommentSaveReq saveReq) {
        DittoComment comment = dittoCommentRepository.findById(dittoCommentId).orElseThrow(NoSuchElementException::new);

        if (!comment.getUser().equals(user)) {
            throw new NoAuthorityException();
        }

        saveReq.modifyEntity(comment);
    }

    public void removeDittoComment(Long dittoCommentId, User user) {
        DittoComment dittoComment = dittoCommentRepository.findById(dittoCommentId).orElseThrow(NoSuchElementException::new);

        if (!dittoComment.getUser().equals(user)) {
            throw new NoAuthorityException();
        }

        dittoCommentRepository.delete(dittoComment);
    }

}