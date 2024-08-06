package site.dittotrip.ditto_trip.spot.stillcut.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class Stillcut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stillcut_id")
    private Long id;

    @Column(name = "stillcut_body")
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

}
