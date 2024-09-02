package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;

@Data
@AllArgsConstructor
@Builder
public class CategoryData {

    private Long categoryId;
    private String name;
    private String imageFilePath;

    private Long myBookmarkId;

    public static CategoryData fromEntity(Category category, CategoryBookmark bookmark) {
        CategoryData categoryData = CategoryData.builder()
                .categoryId(category.getId())
                .name(category.getName())
                .imageFilePath(category.getImagePath())
                .build();

        categoryData.putMyBookmarkId(bookmark);
        return categoryData;
    }

    private void putMyBookmarkId(CategoryBookmark bookmark) {
        if (bookmark != null) {
            this.myBookmarkId = bookmark.getId();
        }
    }

}
