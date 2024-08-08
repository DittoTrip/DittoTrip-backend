package site.dittotrip.ditto_trip.category.domain.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.dto.list.CategoryListRes;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class CategoryListResTest {

    @Test
    void fromEntities() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("가수1", CategoryMajorType.PERSON, CategorySubType.PERSON_SINGER));
        categories.add(new Category("가수2", CategoryMajorType.PERSON, CategorySubType.PERSON_SINGER));
        categories.add(new Category("배우1", CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR));
        categories.add(new Category("배우2", CategoryMajorType.PERSON, CategorySubType.PERSON_ACTOR));
        categories.add(new Category("영화1", CategoryMajorType.CONTENT, CategorySubType.CONTENT_MOVIE));
        categories.add(new Category("영화2", CategoryMajorType.CONTENT, CategorySubType.CONTENT_MOVIE));
        categories.add(new Category("예능1", CategoryMajorType.CONTENT, CategorySubType.CONTENT_ENTERTAINMENT));
        categories.add(new Category("예능2", CategoryMajorType.CONTENT, CategorySubType.CONTENT_ENTERTAINMENT));

        CategoryListRes categoryListRes = CategoryListRes.fromEntities(categories);
        assertThat(categoryListRes.getData().size()).isEqualTo(CategorySubType.values().length);
        assertThat(categoryListRes.getData().get(CategorySubType.PERSON_ACTOR).size()).isEqualTo(2);
        assertThat(categoryListRes.getData().get(CategorySubType.PERSON_COMEDIAN).size()).isEqualTo(0);

        log.info("list length = {}", categoryListRes.getData().size());
        log.info("data = {}", categoryListRes.getData().toString());
    }
}