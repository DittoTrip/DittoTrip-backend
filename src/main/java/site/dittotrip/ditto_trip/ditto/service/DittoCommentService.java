package site.dittotrip.ditto_trip.ditto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoCommentSaveReq;
import site.dittotrip.ditto_trip.ditto.exception.DoubleChildDittoCommentException;
import site.dittotrip.ditto_trip.ditto.repository.DittoCommentRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.exception.common.NoAuthorityException;
import site.dittotrip.ditto_trip.exception.common.NotMatchedRelationException;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DittoCommentService {

    private final DittoRepository dittoRepository;
    private final DittoCommentRepository dittoCommentRepository;
    private final AlarmRepository alarmRepository;

    /**
     * 알림 발생
     *  target : 디토 작성자
     */
    @Transactional(readOnly = false)
    public void saveDittoComment(Long dittoId, Long parentCommentId, User reqUser,
                                 DittoCommentSaveReq saveReq) {
        Ditto ditto = dittoRepository.findById(dittoId).orElseThrow(NoSuchElementException::new);

        DittoComment parentComment = null;
        if (parentCommentId != null) {
            parentComment = dittoCommentRepository.findById(parentCommentId).orElseThrow(NoSuchElementException::new);
            if (parentComment.getParentDittoComment() != null) {
                throw new DoubleChildDittoCommentException();
            }

            if (!parentComment.getDitto().equals(ditto)) {
                throw new NotMatchedRelationException();
            }
        }

        processAlarmInSaveDittoComment(saveReq, ditto, reqUser);

        DittoComment dittoComment = saveReq.toEntity(ditto, reqUser, parentComment);
        dittoCommentRepository.save(dittoComment);
    }

    @Transactional(readOnly = false)
    public void modifyDittoComment(Long dittoCommentId, User reqUser, DittoCommentSaveReq saveReq) {
        DittoComment comment = dittoCommentRepository.findById(dittoCommentId).orElseThrow(NoSuchElementException::new);

        if (comment.getUser().getId() != reqUser.getId()) {
            throw new NoAuthorityException();
        }

        saveReq.modifyEntity(comment);
    }

    @Transactional(readOnly = false)
    public void removeDittoComment(Long dittoCommentId, User reqUser) {
        DittoComment comment = dittoCommentRepository.findById(dittoCommentId).orElseThrow(NoSuchElementException::new);

        if (comment.getUser().getId() != reqUser.getId()) {
            throw new NoAuthorityException();
        }

        dittoCommentRepository.delete(comment);
    }

    private void processAlarmInSaveDittoComment(DittoCommentSaveReq saveReq, Ditto ditto, User reqUser) {
        if (ditto.getUser().getId() == reqUser.getId()) {
            return;
        }

        String title = "작성하신 디토에 댓글이 달렸어요 !!";
        String body = saveReq.getBody();
        String path = "/ditto/" + ditto.getId();
        List<User> targets = new ArrayList<>(List.of(ditto.getUser()));
        alarmRepository.saveAll(Alarm.createAlarms(title, body, path, targets));
    }

}
