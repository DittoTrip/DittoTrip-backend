package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.dto.detail.CategoryDetailRes;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.spot.categoryspot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.categoryspot.repository.CategorySpotRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryDetailService {

    private final CategoryRepository categoryRepository;
    private final CategorySpotRepository categorySpotRepository;

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
