package site.dittotrip.ditto_trip.review.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.image.domain.Image;
import site.dittotrip.ditto_trip.spot.domain.Spot;
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
    private String body;

    private Float rating;

    @Setter
    private Integer likes;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @OneToMany(mappedBy = "review")
    private List<Image> images = new ArrayList<>();

    public Review(String body, Float rating, LocalDateTime createdDateTime, User user, Spot spot, List<Image> images) {
        this.body = body;
        this.rating = rating;
        this.createdDateTime = createdDateTime;
        this.user = user;
        this.spot = spot;
        this.images = images;
    }
}
