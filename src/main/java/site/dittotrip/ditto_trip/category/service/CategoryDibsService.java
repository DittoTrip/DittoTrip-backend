package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.CategoryDibs;
import site.dittotrip.ditto_trip.category.exception.ExistingCategoryDibsException;
import site.dittotrip.ditto_trip.category.repository.CategoryDibsRepository;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

/**
 * 작업 중
 */
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CategoryDibsService {

    private final CategoryDibsRepository categoryDibsRepository;
    private final CategoryRepository categoryRepository;

    private void addCategoryDibs(Long categoryId, User user) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        categoryDibsRepository.findByCategoryAndUser(category, user).ifPresent(m -> {
            throw new ExistingCategoryDibsException();
        });

        CategoryDibs categoryDibs = new CategoryDibs(category, user);
        categoryDibsRepository.save(categoryDibs);
    }

//    private void removeCategoryDibs(Long categoryDibsId, User user) {
//        if
//    }

}
