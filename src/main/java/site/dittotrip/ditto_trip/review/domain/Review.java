package site.dittotrip.ditto_trip.review.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "review_body")
    @Setter
    private String body;

    @Setter
    private Float rating;

    @Setter
    private Integer likes = 0;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "spot_visit_id")
    private SpotVisit spotVisit;

    @OneToMany(mappedBy = "review")
    private List<ReviewImage> reviewImages = new ArrayList<>();

//    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
//    private List<ReviewComment> reviewComments = new ArrayList<>();

    public Review(String body, Float rating, LocalDateTime createdDateTime, User user, SpotVisit spotVisit) {
        this.body = body;
        this.rating = rating;
        this.createdDateTime = createdDateTime;
        this.user = user;
        this.spotVisit = spotVisit;
    }

}
