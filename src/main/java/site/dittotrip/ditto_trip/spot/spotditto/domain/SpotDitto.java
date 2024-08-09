package site.dittotrip.ditto_trip.spot.spotditto.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.user.domain.User;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Data
public class SpotDitto {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "spot_ditto_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    public SpotDitto(Spot spot, User user) {
        this.spot = spot;
        this.user = user;
    }

}