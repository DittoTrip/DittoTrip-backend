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
public class SpotApplyImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_apply_image_id")
    private Long id;

    private String imagePath;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_apply_id")
    private SpotApply spotApply;

    public SpotApplyImage(String imagePath, SpotApply spotApply) {
        this.imagePath = imagePath;
        this.spotApply = spotApply;
    }

}
