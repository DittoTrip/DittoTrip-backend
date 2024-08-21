package site.dittotrip.ditto_trip.review.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class ReviewLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reivew_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public ReviewLike(Review review, User user) {
        this.review = review;
        this.user = user;
    }

}
