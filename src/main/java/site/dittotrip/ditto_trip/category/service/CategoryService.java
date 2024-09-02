package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.domain.dto.*;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.repository.CategorySpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryBookmarkRepository categoryBookmarkRepository;
    private final CategorySpotRepository categorySpotRepository;
    private final SpotRepository spotRepository;

    public CategoryPageRes findCategoryList(CategorySubType subType, Pageable pageable) {
        Page<Category> page = categoryRepository.findBySubType(subType, pageable);
        return CategoryPageRes.fromEntities(page);
    }


    public CategoryMajorTypeListRes findCategoryListByBookmark(User user, CategoryMajorType majorType, Pageable pageable) {
        Page<CategoryBookmark> page = categoryBookmarkRepository.findByUserAndMajorType(user, majorType, pageable);

        List<Category> categories = new ArrayList<>();
        for (CategoryBookmark bookmark : page.getContent()) {
            categories.add(bookmark.getCategory());
        }

        return CategoryMajorTypeListRes.fromEntities(categories, page.getTotalPages());
    }

    /**
     * 카테고리 검색 조회
     *  - 단순 문자열 포함 검색
     */
    public CategoryMajorTypeListRes findCategoryListBySearch(String word, CategoryMajorType majorType, Pageable pageable) {
        Page<Category> page = categoryRepository.findBySearchAndMajorType(word, majorType, pageable);
        return CategoryMajorTypeListRes.fromEntities(page.getContent(), page.getTotalPages());
    }

    /**
     * 카테고리 타입마다 나누지 않고 하나의 리스트로 반환합니다.
     */
    public CategoryListNoTypeRes findCategoryNoTypeListBySearch(String word) {
        List<Category> categories = categoryRepository.findByNameContaining(word);
        return CategoryListNoTypeRes.fromEntities(categories);
    }

    @Transactional(readOnly = false)
    public void saveCategory(CategorySaveReq categorySaveReq, MultipartFile multipartFile) {

        // 이미지 처리
        String imagePath = null;

        Category category = categorySaveReq.toEntity(imagePath);
        categoryRepository.save(category);

        List<Long> spotIds = categorySaveReq.getSpotIds();
        for (Long spotId : spotIds) {
            Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);
            categorySpotRepository.save(new CategorySpot(category, spot));
        }

        // 해시태그 처리
    }

    @Transactional(readOnly = false)
    public void modifyCategory(Long categoryId, CategoryModifyReq categoryModifyReq, MultipartFile multipartFile) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        String imagePath = null;
        if (multipartFile != null) {
            // 이미지 처리
        }

        categoryModifyReq.modifyEntity(category, imagePath);

        for (Long removeSpotId : categoryModifyReq.getRemoveSpotIds()) {
            Spot spot = spotRepository.findById(removeSpotId).orElseThrow(NoSuchElementException::new);
            CategorySpot categorySpot = categorySpotRepository.findByCategoryAndSpot(category, spot).orElseThrow(NoSuchElementException::new);
            categorySpotRepository.delete(categorySpot);
        }

        for (Long newSpotId : categoryModifyReq.getNewSpotIds()) {
            Spot spot = spotRepository.findById(newSpotId).orElseThrow(NoSuchElementException::new);
            categorySpotRepository.save(new CategorySpot(category, spot));
        }

        // 해시태그 처리
    }

    @Transactional(readOnly = false)
    public void removeCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        categoryRepository.delete(category);
    }

}
