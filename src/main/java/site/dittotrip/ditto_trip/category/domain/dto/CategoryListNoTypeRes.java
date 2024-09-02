package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListNoTypeRes {

    private List<CategoryData> categoryDataList = new ArrayList<>();

    public static CategoryListNoTypeRes fromEntities(List<Category> categories) {
        CategoryListNoTypeRes categoryListNoTypeRes = new CategoryListNoTypeRes();
        for (Category category : categories) {
            categoryListNoTypeRes.getCategoryDataList().add(CategoryData.fromEntity(category));
        }
        return categoryListNoTypeRes;
    }

}
