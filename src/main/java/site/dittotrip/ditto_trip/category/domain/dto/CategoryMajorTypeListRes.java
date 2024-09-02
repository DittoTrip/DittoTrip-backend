package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static site.dittotrip.ditto_trip.category.domain.dto.CategoryData.*;

@Data
public class CategoryMajorTypeListRes {

    private List<CategoryData> categoryDataList = new ArrayList<>();
    private Integer totalPages;

}
