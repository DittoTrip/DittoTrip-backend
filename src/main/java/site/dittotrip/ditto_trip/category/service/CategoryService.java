package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryDetailRes;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.repository.CategorySpotRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes.fromEntities;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorySpotRepository categorySpotRepository;
    private final CategoryBookmarkRepository categoryBookmarkRepository;

    private static final int PAGE_SIZE = 10;

    public CategoryListRes findCategoryList(CategoryMajorType majorType) {
        PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);
        Page<Category> page = categoryRepository.findByMajorType(majorType, pageRequest);

        return fromEntities(page.getContent());
    }

    /**
     * 북마크 카테고리 리스트 조회 (작업 중)
     *  -> 페이지네이션 x
     */
    public CategoryListRes findBookmarkCategoryList(User user) {
        return null;
    }


    /**
     * 카테고리 검색 조회
     *  리스트 형태인가 ?
     *  equal 검색 ? 포함 검색 ? 공백 제거 후 매칭 ?
     */
    public CategoryListRes findCategoryListBySearch(String word) {
        List<Category> categories = categoryRepository.findByCategoryNameContaining(word);
        return fromEntities(categories);
    }

    /**
     * 미완성
     * - findByScope 메서드 수정
     */
    public CategoryDetailRes findCategoryDetail(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        List<CategorySpot> categorySpots = categorySpotRepository.findByScope(category);

        return CategoryDetailRes.fromEntity(category, convertSpotList(categorySpots));
    }

    private List<Spot> convertSpotList(List<CategorySpot> categorySpots) {
        List<Spot> spots = new ArrayList<>();
        for (CategorySpot categorySpot : categorySpots) {
            spots.add(categorySpot.getSpot());
        }
        return spots;
    }

}
