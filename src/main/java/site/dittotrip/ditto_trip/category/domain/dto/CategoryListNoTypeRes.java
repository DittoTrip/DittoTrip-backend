package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListNoTypeRes {

    private List<CategoryData> categoryDataList = new ArrayList<>();

    private Integer totalPages;

}
