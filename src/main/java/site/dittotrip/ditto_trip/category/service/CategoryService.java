package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.dto.detail.CategoryDetailRes;
import site.dittotrip.ditto_trip.category.domain.dto.list.CategoryListRes;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.spot.categoryspot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.categoryspot.repository.CategorySpotRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static site.dittotrip.ditto_trip.category.domain.dto.list.CategoryListRes.fromEntities;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategorySpotRepository categorySpotRepository;

    /**
     * 카테고리 리스트 반환 | frame 5
     *  페이지네이션 정책 정해지면 페이지네이션 수정
     */
    public CategoryListRes findCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();
        return fromEntities(categoryList);
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
