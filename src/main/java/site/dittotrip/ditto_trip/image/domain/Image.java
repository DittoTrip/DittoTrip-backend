package site.dittotrip.ditto_trip.image.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.image.domain.enumerated.ForeignType;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.stillcut.domain.StillCut;

import static jakarta.persistence.FetchType.LAZY;

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

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "still_cut_id")
    private StillCut stillCut;

    // Review Mapping

}
