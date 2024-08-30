package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryPageRes;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

import static site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes.fromEntities;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryBookmarkRepository categoryBookmarkRepository;

    public CategoryPageRes findCategoryList(CategorySubType subType, Pageable pageable) {
        Page<Category> page = categoryRepository.findBySubType(subType, pageable);
        return CategoryPageRes.fromEntities(page);
    }

    public CategoryListRes findCategoryListByBookmark(User user) {
        List<CategoryBookmark> bookmarks = categoryBookmarkRepository.findByUser(user);

        List<Category> categories = new ArrayList<>();
        for (CategoryBookmark bookmark : bookmarks) {
            categories.add(bookmark.getCategory());
        }

        return fromEntities(categories);
    }

    /**
     * 카테고리 검색 조회
     *  - 단순 문자열 포함 검색
     */
    public CategoryListRes findCategoryListBySearch(String word) {
        List<Category> categories = categoryRepository.findByNameContaining(word);
        return fromEntities(categories);
    }



}
