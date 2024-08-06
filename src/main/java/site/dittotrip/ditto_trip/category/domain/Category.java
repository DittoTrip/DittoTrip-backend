package site.dittotrip.ditto_trip.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.hashtag.domain.entity.CategoryHashtag;
import site.dittotrip.ditto_trip.spot.categoryspot.domain.CategorySpot;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String categoryName;
    private CategoryMajorType categoryMajorType;
    private CategorySubType categorySubType;

    @OneToMany(mappedBy = "category")
    private List<CategoryHashtag> categoryHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<CategorySpot> categorySpots = new ArrayList<>();

    /**
     * for CategoryListRes test
     */
    public Category(String categoryName, CategoryMajorType categoryMajorType, CategorySubType categorySubType) {
        this.categoryName = categoryName;
        this.categoryMajorType = categoryMajorType;
        this.categorySubType = categorySubType;
    }
}