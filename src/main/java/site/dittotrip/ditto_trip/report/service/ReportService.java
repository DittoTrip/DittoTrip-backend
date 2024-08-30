package site.dittotrip.ditto_trip.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.ditto.repository.DittoCommentRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.report.domain.Report;
import site.dittotrip.ditto_trip.report.domain.dto.ReportSaveReq;
import site.dittotrip.ditto_trip.report.exception.ReportTargetException;
import site.dittotrip.ditto_trip.report.repository.ReportRepository;
import site.dittotrip.ditto_trip.review.repository.ReviewCommentRepository;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final DittoRepository dittoRepository;
    private final DittoCommentRepository dittoCommentRepository;

    public void findReportList() {

    }

    @Transactional(readOnly = false)
    public void saveReport(User user, ReportSaveReq saveReq) {
        Object targetEntity = getTargetEntity(saveReq);
        Report report = saveReq.toEntity(targetEntity, user);
        reportRepository.save(report);
    }

    @Transactional(readOnly = false)
    public void handleReport() {

    }

    private Object getTargetEntity(ReportSaveReq saveReq) {
        Long targetId = saveReq.getTargetId();

        Object targetEntity = null;
        switch (saveReq.getReportTargetType()) {
            case REVIEW:
                targetEntity = reviewRepository.findById(targetId).orElseThrow(NoSuchElementException::new);
                break;
            case REVIEW_COMMENT:
                targetEntity = reviewCommentRepository.findById(targetId).orElseThrow(NoSuchElementException::new);
                break;
            case DITTO:
                targetEntity = dittoRepository.findById(targetId).orElseThrow(NoSuchElementException::new);
                break;
            case DITTO_COMMENT:
                targetEntity = dittoCommentRepository.findById(targetId).orElseThrow(NoSuchElementException::new);
                break;
            default:
                throw new ReportTargetException();
        }
        return targetEntity;
    }

}
