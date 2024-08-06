package site.dittotrip.ditto_trip.image.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.image.domain.enumerated.ForeignType;

@Entity
@NoArgsConstructor
@Getter
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String filePath;

    @Enumerated(EnumType.STRING)
    private ForeignType foreignType;

    // Category Mapping

    // Spot Mapping

    // Review Mapping

}
