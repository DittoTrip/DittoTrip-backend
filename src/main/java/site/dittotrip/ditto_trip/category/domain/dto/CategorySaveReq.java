package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CategorySaveReq {

    private String name;
    private CategoryMajorType categoryMajorType;
    private CategorySubType categorySubType;

    List<String> hashtagNames = new ArrayList<>();
    List<Long> spotIds = new ArrayList<>();

    public Category toEntity() {
        return new Category(name, categoryMajorType, categorySubType);
    }

}
