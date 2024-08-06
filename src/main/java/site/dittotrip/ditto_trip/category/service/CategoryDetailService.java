package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryDetailRes;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryDetailService {

    private final CategoryRepository categoryRepository;

    public CategoryDetailRes findCategoryDetail(Long categoryId) {
        return null;
    }

}
