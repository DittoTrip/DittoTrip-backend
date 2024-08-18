package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.image.domain.Image;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.time.LocalDateTime;

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

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @OneToOne(mappedBy = "stillCut")
    private Image image;

}
