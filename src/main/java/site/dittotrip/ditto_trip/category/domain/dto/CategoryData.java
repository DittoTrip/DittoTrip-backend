package site.dittotrip.ditto_trip.category.domain.dto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagCategory;
import site.dittotrip.ditto_trip.utils.Language;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CategoryData {

    private Long categoryId;
    private String name;
    private CategoryMajorType majorType;
    private CategorySubType subType;
    private String imageFilePath;

    private Long myBookmarkId;
    @Builder.Default
    private List<String> hashtags = new ArrayList<>();

    public static CategoryData fromEntity(Category category, CategoryBookmark bookmark) {
        Language language = Language.KO;

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            language = request.getHeader("Accept-Language") != null && request.getHeader("Accept-Language").equals("en") ? Language.EN : Language.KO;
        }

        CategoryData categoryData = CategoryData.builder()
                .categoryId(category.getId())
                .name(language == Language.EN ? category.getNameEN() : category.getName())
                .majorType(category.getCategoryMajorType())
                .subType(category.getCategorySubType())
                .imageFilePath(category.getImagePath())
                .build();

        categoryData.putMyBookmarkId(bookmark);
        categoryData.putHashtags(category);
        return categoryData;
    }

    private void putMyBookmarkId(CategoryBookmark bookmark) {
        if (bookmark != null) {
            this.myBookmarkId = bookmark.getId();
        }
    }

    private void putHashtags(Category category) {
        for (HashtagCategory hashtagCategory : category.getHashtagCategories()) {
            this.hashtags.add(hashtagCategory.getHashtag().getName());
        }
    }

}
