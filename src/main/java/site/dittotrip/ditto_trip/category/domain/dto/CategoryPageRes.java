package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryPageRes {

    private List<CategoryData> categoryDataList = new ArrayList<>();

    public static CategoryPageRes fromEntities(List<Category> categories) {
        CategoryPageRes categoryPageRes = new CategoryPageRes();

        for (Category category : categories) {
            categoryPageRes.getCategoryDataList().add(CategoryData.fromEntity(category));
        }

        return categoryPageRes;
    }

}
