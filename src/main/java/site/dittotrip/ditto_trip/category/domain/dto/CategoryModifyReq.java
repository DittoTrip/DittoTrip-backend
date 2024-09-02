package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryModifyReq {

    private String name;
    private CategoryMajorType categoryMajorType;
    private CategorySubType categorySubType;

    List<String> removeHashtags = new ArrayList<>();
    List<String> newHashtags = new ArrayList<>();
    List<Long> removeSpotIds = new ArrayList<>();
    List<Long> newSpotIds = new ArrayList<>();

    public void modifyEntity(Category category, String imagePath) {
        category.setName(name);
        category.setCategoryMajorType(categoryMajorType);
        category.setCategorySubType(categorySubType);
        if (imagePath != null) {
            category.setImagePath(imagePath);
        }
    }

}
