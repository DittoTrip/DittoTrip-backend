package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.category.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryPageRes {

    private List<CategoryData> categoryDataList = new ArrayList<>();
    private Integer totalPages;

    public static CategoryPageRes fromEntities(Page<Category> page) {
        List<Category> categories = page.getContent();
        CategoryPageRes categoryPageRes = new CategoryPageRes();
        categoryPageRes.setTotalPages(page.getTotalPages());

        for (Category category : categories) {
            categoryPageRes.getCategoryDataList().add(CategoryData.fromEntity(category));
        }

        return categoryPageRes;
    }

}
