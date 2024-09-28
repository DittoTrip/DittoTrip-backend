package site.dittotrip.ditto_trip.searchpage.dto;


import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.mainpage.domain.dto.CategoryMainPageData;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategorySearchPageData {

    private Long categoryId;
    private String name;
    private String imagePath;

    public static CategorySearchPageData fromEntity(Category category) {
        return CategorySearchPageData.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .imagePath(category.getImagePath())
                .build();
    }

}
