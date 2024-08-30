package site.dittotrip.ditto_trip.report.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.report.domain.Report;
import site.dittotrip.ditto_trip.report.domain.enums.ReportReasonType;
import site.dittotrip.ditto_trip.report.domain.enums.ReportTargetType;
import site.dittotrip.ditto_trip.report.exception.ReportTargetTypeException;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.time.LocalDateTime;

@Data
@Builder
public class ReportData {

    private Long reportId;
    private ReportReasonType reportReasonType;
    private ReportTargetType reportTargetType;
    private String body;
    private Boolean isHandled;
    private LocalDateTime createdDateTime;

    private UserData userData;
    private Long targetId;

    public static ReportData fromEntity(Report report) {
        ReportData reportData = ReportData.builder()
                .reportId(report.getId())
                .reportReasonType(report.getReportReasonType())
                .reportTargetType(report.getReportTargetType())
                .body(report.getBody())
                .isHandled(report.getIsHandled())
                .createdDateTime(report.getCreatedDateTime())
                .userData(UserData.fromEntity(report.getUser()))
                .build();

        reportData.putTargetId(report);
        return reportData;
    }

    private void putTargetId(Report report) {
        switch (report.getReportTargetType()) {
            case REVIEW:
                this.setTargetId(report.getReview().getId());
                break;
            case REVIEW_COMMENT:
                this.setTargetId(report.getReviewComment().getId());
                break;
            case DITTO:
                this.setTargetId(report.getDitto().getId());
                break;
            case DITTO_COMMENT:
                this.setTargetId(report.getDittoComment().getId());
                break;
            default:
                throw new ReportTargetTypeException();
        }
    }

}
