package site.dittotrip.ditto_trip.report.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.report.domain.Report;
import site.dittotrip.ditto_trip.report.domain.enums.ReportTargetType;
import site.dittotrip.ditto_trip.report.domain.enums.ReportType;
import site.dittotrip.ditto_trip.report.exception.ReportTargetException;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.user.domain.User;

@Data
@NoArgsConstructor
public class ReportSaveReq {

    private ReportType reportType;
    private ReportTargetType reportTargetType;
    private String body;

    private Long targetId;

    public Report toEntity(Object targetEntity, User user) {
        Report report = new Report(reportType, reportTargetType, body, user);
        putReportEntity(report, targetEntity);
        return report;
    }

    private void putReportEntity(Report report, Object targetEntity) {
        switch (reportTargetType) {
            case REVIEW:
                report.setReview((Review) targetEntity);
                break;
            case REVIEW_COMMENT:
                report.setReviewComment((ReviewComment) targetEntity);
                break;
            case DITTO:
                report.setDitto((Ditto) targetEntity);
                break;
            case DITTO_COMMENT:
                report.setDittoComment((DittoComment) targetEntity);
                break;
            default:
                throw new ReportTargetException();
        }
    }

}
