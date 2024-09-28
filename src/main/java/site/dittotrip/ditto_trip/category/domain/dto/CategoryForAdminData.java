package site.dittotrip.ditto_trip.category.domain.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotMiniData;
import site.dittotrip.ditto_trip.utils.Language;

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
        Language language = Language.KO;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            language = request.getHeader("Accept-Language") != null && request.getHeader("Accept-Language").equals("en") ? Language.EN : Language.KO;
        }

        return CategoryForAdminData.builder()
                .categoryId(category.getId())
            .name(language == Language.EN ? category.getNameEN() : category.getName())
                .subType(category.getCategorySubType())
                .createdDateTime(category.getCreatedDateTime())
                .categorySpotCount(category.getCategorySpots().size())
                .build();
    }

}
