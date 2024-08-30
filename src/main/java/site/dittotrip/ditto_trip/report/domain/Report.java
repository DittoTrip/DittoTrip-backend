package site.dittotrip.ditto_trip.report.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.report.domain.enums.ReportTarget;
import site.dittotrip.ditto_trip.report.domain.enums.ReportType;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Enumerated(EnumType.STRING)
    private ReportTarget reportTarget;

    private String body;

    private Boolean isHandled = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_comment_id")
    private ReviewComment reviewComment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ditto_id")
    private Ditto ditto;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ditto_comment_id")
    private DittoComment dittoComment;

    public Report(ReportType reportType, ReportTarget reportTarget, String body, Review review, ReviewComment reviewComment, Ditto ditto, DittoComment dittoComment) {
        this.reportType = reportType;
        this.reportTarget = reportTarget;
        this.body = body;
        this.review = review;
        this.reviewComment = reviewComment;
        this.ditto = ditto;
        this.dittoComment = dittoComment;
    }

}
