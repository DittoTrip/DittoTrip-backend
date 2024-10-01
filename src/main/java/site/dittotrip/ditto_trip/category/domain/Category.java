package site.dittotrip.ditto_trip.category.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagCategory;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.CategorySpotApply;

import java.time.LocalDateTime;
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
    @Column(name = "name_en")
    @Setter
    private String nameEN;
    @Setter
    @Enumerated(EnumType.STRING)
    private CategoryMajorType categoryMajorType;
    @Setter
    @Enumerated(EnumType.ORDINAL)
    private CategorySubType categorySubType;
    @Setter
    private String imagePath;

    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<HashtagCategory> hashtagCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<CategorySpot> categorySpots = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<CategorySpotApply> categorySpotApplies = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<CategoryBookmark> categoryBookmarks = new ArrayList<>();


    /**
     * for CategoryListRes test
     */
    public Category(String name, CategoryMajorType categoryMajorType, CategorySubType categorySubType) {
        this.name = name;
        this.categoryMajorType = categoryMajorType;
        this.categorySubType = categorySubType;
    }

}