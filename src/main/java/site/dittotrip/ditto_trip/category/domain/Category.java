package site.dittotrip.ditto_trip.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Setter
    private String name;
    @Setter
    private CategoryMajorType categoryMajorType;
    @Setter
    private CategorySubType categorySubType;
    @Setter
    private String imagePath;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.REMOVE)
    private List<Hashtag> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<CategorySpot> categorySpots = new ArrayList<>();

    /**
     * for CategoryListRes test
     */
    public Category(String name, CategoryMajorType categoryMajorType, CategorySubType categorySubType, String imagePath) {
        this.name = name;
        this.categoryMajorType = categoryMajorType;
        this.categorySubType = categorySubType;
        this.imagePath = imagePath;
    }
}