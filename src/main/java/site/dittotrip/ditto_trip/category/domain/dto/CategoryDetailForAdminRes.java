package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;

@Data
@AllArgsConstructor
public class CategoryDetailForAdminRes {

    private CategoryDetailForAdminData categoryDetailForAdminData;

    public static CategoryDetailForAdminRes fromEntity(Category category) {
        return new CategoryDetailForAdminRes(CategoryDetailForAdminData.fromEntity(category));
    }

}
