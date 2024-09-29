package site.dittotrip.ditto_trip.mainpage.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategoryMainPageData {

    private Long categoryId;
    private String name;
    private String imagePath;

    public static List<CategoryMainPageData> fromEntities(List<Category> categories) {
        List<CategoryMainPageData> dataList = new ArrayList<>();
        for (Category category : categories) {
            dataList.add(fromEntity(category));
        }
        return dataList;
    }

    private static CategoryMainPageData fromEntity(Category category) {
        return CategoryMainPageData.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .imagePath(category.getImagePath())
                .build();
    }

}
