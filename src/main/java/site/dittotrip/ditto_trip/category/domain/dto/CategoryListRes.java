package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static site.dittotrip.ditto_trip.category.domain.dto.CategoryData.*;

@Data
public class CategoryListRes {

    private Map<CategorySubType, List<CategoryData>> data = new HashMap<>();

    public CategoryListRes() {
        CategorySubType[] types = CategorySubType.values();
        for (CategorySubType type : types) {
            data.put(type, new ArrayList<>());
        }
    }

    public static CategoryListRes fromEntities(List<Category> categories) {
        CategoryListRes categoryListRes = new CategoryListRes();
        Map<CategorySubType, List<CategoryData>> data = categoryListRes.getData();

        for (Category category : categories) {
            data.get(category.getCategorySubType()).add(fromEntity(category));
        }

        return categoryListRes;
    }

}
