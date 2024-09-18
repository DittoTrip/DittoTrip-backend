package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class SpotImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_image_id")
    private Long id;

    private String imagePath;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    public SpotImage(String imagePath, Spot spot) {
        this.imagePath = imagePath;
        this.spot = spot;
    }

}
