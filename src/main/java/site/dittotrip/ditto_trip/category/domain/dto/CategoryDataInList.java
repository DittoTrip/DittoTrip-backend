package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;

@Data
@AllArgsConstructor
public class CategoryDataInList {
    private String categoryName;
    // 카테고리 이미지

    public static CategoryDataInList fromEntity(Category category) {
        return new CategoryDataInList(category.getCategoryName());
    }

}
