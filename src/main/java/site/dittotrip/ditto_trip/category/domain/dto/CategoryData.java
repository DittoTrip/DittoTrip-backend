package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;

@Data
@AllArgsConstructor
@Builder
public class CategoryData {

    private Long categoryId;
    private String name;
    private String imageFilePath;

    public static CategoryData fromEntity(Category category) {

        return CategoryData.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .imageFilePath(category.getImagePath())
                .build();
    }

}
