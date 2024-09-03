package site.dittotrip.ditto_trip.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.ditto.repository.DittoCommentRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.report.domain.Report;
import site.dittotrip.ditto_trip.report.domain.dto.ReportHandleReq;
import site.dittotrip.ditto_trip.report.domain.dto.ReportListRes;
import site.dittotrip.ditto_trip.report.domain.dto.ReportSaveReq;
import site.dittotrip.ditto_trip.report.exception.ReportTargetTypeException;
import site.dittotrip.ditto_trip.report.repository.ReportRepository;
import site.dittotrip.ditto_trip.review.repository.ReviewCommentRepository;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.enums.UserStatus;
import site.dittotrip.ditto_trip.utils.RedisService;

import java.time.LocalDateTime;
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

    public ReportListRes findReportList(Pageable pageable) {
        Page<Report> page = reportRepository.findAll(pageable);
        return ReportListRes.fromEntities(page.getContent(), page.getTotalPages());
    }

    @Transactional(readOnly = false)
    public void saveReport(User user, ReportSaveReq saveReq) {
        Object targetEntity = getTargetEntity(saveReq);
        Report report = saveReq.toEntity(targetEntity, user);
        reportRepository.save(report);
    }

    @Transactional(readOnly = false)
    public void handleReport(Long reportId, ReportHandleReq handleReq) {
        Report report = reportRepository.findById(reportId).orElseThrow(NoSuchElementException::new);

        Boolean shouldDeleteContent = handleReq.getShouldDeleteContent();
        Boolean shouldPermanentlyBan = handleReq.getShouldPermanentlyBan();
        Integer suspensionDays = handleReq.getSuspensionDays();

        if (shouldDeleteContent) {
            deleteContent(report);
        }

        User targetUser = getTargetUser(report);
        if (shouldPermanentlyBan) {
            targetUser.setUserStatus(UserStatus.PERMANENTLY_BANNED);
        }

        if (suspensionDays != null) {
            LocalDateTime suspendedDataTime = LocalDateTime.now().plusDays(suspensionDays);
            targetUser.setSuspendedDateTime(suspendedDataTime);
        }
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
                throw new ReportTargetTypeException();
        }
        return targetEntity;
    }

    private void deleteContent(Report report) {
        switch (report.getReportTargetType()) {
            case REVIEW:
                reviewRepository.delete(report.getReview());
                break;
            case REVIEW_COMMENT:
                reviewCommentRepository.delete(report.getReviewComment());
                break;
            case DITTO:
                dittoRepository.delete(report.getDitto());
                break;
            case DITTO_COMMENT:
                dittoCommentRepository.delete(report.getDittoComment());
                break;
            default:
                throw new ReportTargetTypeException();
        }
    }

    private User getTargetUser(Report report) {
        return switch (report.getReportTargetType()) {
            case REVIEW -> report.getReview().getUser();
            case REVIEW_COMMENT -> report.getReviewComment().getUser();
            case DITTO -> report.getDitto().getUser();
            case DITTO_COMMENT -> report.getDittoComment().getUser();
            default -> throw new ReportTargetTypeException();
        };
    }

}
