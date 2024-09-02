package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CategorySaveReq {

    private String name;
    private CategoryMajorType categoryMajorType;
    private CategorySubType categorySubType;

    List<String> hashtags = new ArrayList<>();
    List<Long> spotIds = new ArrayList<>();

    public Category toEntity(String imagePath) {
        return new Category(name, categoryMajorType, categorySubType, imagePath);
    }

}
