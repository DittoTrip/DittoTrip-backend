package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.category.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListForAdminRes {

    private List<CategoryForAdminData> categoryForAdminDataList = new ArrayList<>();

    private Integer totalPages;

    public static CategoryListForAdminRes fromEntities(Page<Category> page) {
        CategoryListForAdminRes res = new CategoryListForAdminRes();
        res.setCategoryForAdminDataList(CategoryForAdminData.fromEntities(page.getContent()));
        res.setTotalPages(page.getTotalPages());
        return res;
    }

}
