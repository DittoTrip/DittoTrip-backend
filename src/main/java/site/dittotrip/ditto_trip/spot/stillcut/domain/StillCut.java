package site.dittotrip.ditto_trip.spot.stillcut.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.image.domain.Image;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class StillCut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "still_cut_id")
    private Long id;

    @Column(name = "still_cut_body")
    private String body;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @OneToOne(mappedBy = "stillCut")
    private Image image;

}
