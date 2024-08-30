package site.dittotrip.ditto_trip.report.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.report.domain.enums.ReportTargetType;
import site.dittotrip.ditto_trip.report.domain.enums.ReportType;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.user.domain.User;

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
    private ReportTargetType reportTargetType;

    private String body;

    private Boolean isHandled = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime localDateTime;

    // 신고자
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    /**
     * 신고 대상 :
     *  Review / ReviewComment / Ditto / DittoComment
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    @Setter
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_comment_id")
    @Setter
    private ReviewComment reviewComment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ditto_id")
    @Setter
    private Ditto ditto;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ditto_comment_id")
    @Setter
    private DittoComment dittoComment;

    public Report(ReportType reportType, ReportTargetType reportTargetType, String body, User user) {
        this.reportType = reportType;
        this.reportTargetType = reportTargetType;
        this.body = body;
        this.user = user;
    }

}
