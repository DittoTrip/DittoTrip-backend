package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class SpotVisit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_visit_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "users_id")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

}
