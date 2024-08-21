package site.dittotrip.ditto_trip.review.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@NoArgsConstructor
@Getter
public class ReviewComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_comment_id")
    private Long id;

    @Setter
    private String body;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_comment_id")
    private ReviewComment parentReviewComment = null;

    @OneToMany(mappedBy = "parentReviewComment")
    private List<ReviewComment> childReviewComments = new ArrayList<>();

    public ReviewComment(String body, User user, Review review, ReviewComment parentReviewComment) {
        this.body = body;
        this.user = user;
        this.review = review;
        this.parentReviewComment = parentReviewComment;
    }

}
