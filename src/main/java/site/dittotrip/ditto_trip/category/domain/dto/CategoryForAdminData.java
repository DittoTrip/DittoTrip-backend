package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotMiniData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategoryForAdminData {

    private Long categoryId;
    private String name;
    private CategorySubType subType;
    private LocalDateTime createdDateTime;

    private Integer categorySpotCount;

    public static List<CategoryForAdminData> fromEntities(List<Category> categories) {
        List<CategoryForAdminData> dataList = new ArrayList<>();
        for (Category category : categories) {
            dataList.add(CategoryForAdminData.fromEntity(category));
        }
        return dataList;
    }

    public static CategoryForAdminData fromEntity(Category category) {
        return CategoryForAdminData.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .subType(category.getCategorySubType())
                .createdDateTime(category.getCreatedDateTime())
                .categorySpotCount(category.getCategorySpots().size())
                .build();
    }

}
