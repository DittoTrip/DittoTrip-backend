package site.dittotrip.ditto_trip.spot.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter
public class CategorySpot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_spot_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    /**
     * for test
     */
    public CategorySpot(Category category, Spot spot) {
        this.category = category;
        this.spot = spot;
    }

}
