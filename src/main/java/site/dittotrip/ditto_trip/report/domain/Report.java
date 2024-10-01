package site.dittotrip.ditto_trip.report.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.report.domain.enums.ReportTargetType;
import site.dittotrip.ditto_trip.report.domain.enums.ReportReasonType;
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
    private ReportReasonType reportReasonType;

    @Enumerated(EnumType.STRING)
    private ReportTargetType reportTargetType;

    private Boolean isHandled = Boolean.FALSE;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    // 신고자
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    /**
     * 신고 대상 :
     *  Review / ReviewComment / Ditto / DittoComment
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Setter
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_comment_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Setter
    private ReviewComment reviewComment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ditto_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Setter
    private Ditto ditto;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ditto_comment_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Setter
    private DittoComment dittoComment;

    public Report(ReportReasonType reportReasonType, ReportTargetType reportTargetType, User user) {
        this.reportReasonType = reportReasonType;
        this.reportTargetType = reportTargetType;
        this.user = user;
    }

}
