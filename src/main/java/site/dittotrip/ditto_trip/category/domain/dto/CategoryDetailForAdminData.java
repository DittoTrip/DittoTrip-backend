package site.dittotrip.ditto_trip.category.domain.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagCategory;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotMiniData;
import site.dittotrip.ditto_trip.utils.Language;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategoryDetailForAdminData {

    private Long categoryId;
    private String name;
    private CategoryMajorType majorType;
    private CategorySubType subType;

    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    @Builder.Default
    private List<SpotMiniData> spotMiniDataList = new ArrayList<>();

    public static CategoryDetailForAdminData fromEntity(Category category) {
        Language language = Language.KO;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            language = request.getHeader("Accept-Language") != null && request.getHeader("Accept-Language").equals("en") ? Language.EN : Language.KO;
        }

        CategoryDetailForAdminData data = CategoryDetailForAdminData.builder()
                .categoryId(category.getId())
            .name(language == Language.EN ? category.getNameEN() : category.getName())
                .majorType(category.getCategoryMajorType())
                .subType(category.getCategorySubType())
                .build();

        data.putHashtags(category);
        data.putSpotMiniDataList(category);

        return data;
    }

    private void putHashtags(Category category) {
        for (HashtagCategory hashtagCategory : category.getHashtagCategories()) {
            this.hashtags.add(hashtagCategory.getHashtag().getName());
        }
    }

    private void putSpotMiniDataList(Category category) {
        for (CategorySpot categorySpot : category.getCategorySpots()) {
            this.spotMiniDataList.add(SpotMiniData.fromEntity(categorySpot.getSpot()));
        }
    }

}
