package site.dittotrip.ditto_trip.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.hashtag.domain.entity.CategoryHashtag;
import site.dittotrip.ditto_trip.image.domain.Image;
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

    private String categoryName;
    private CategoryMajorType categoryMajorType;
    private CategorySubType categorySubType;
    private String imagePath;

    @OneToMany(mappedBy = "category")
    private List<CategoryHashtag> categoryHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<CategorySpot> categorySpots = new ArrayList<>();

    /**
     * for CategoryListRes test
     */
    public Category(String categoryName, CategoryMajorType categoryMajorType, CategorySubType categorySubType, String imagePath) {
        this.categoryName = categoryName;
        this.categoryMajorType = categoryMajorType;
        this.categorySubType = categorySubType;
        this.imagePath = imagePath;
    }
}