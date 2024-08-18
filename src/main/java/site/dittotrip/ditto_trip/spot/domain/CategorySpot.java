package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import static jakarta.persistence.FetchType.LAZY;

/**
 * 없애 / @ManyToMany
 */
@Entity
@NoArgsConstructor
@Getter
public class CategorySpot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_spot_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Category category;

    @ManyToOne(fetch = LAZY)
    private Spot spot;

}
