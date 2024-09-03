package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.exception.ExistingCategoryDibsException;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 작업 중
 */
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CategoryBookmarkService {

    private final CategoryBookmarkRepository categoryBookmarkRepository;
    private final CategoryRepository categoryRepository;

    public Boolean findCategoryBookmark(User user, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        Optional<CategoryBookmark> optionalBookmark = categoryBookmarkRepository.findByCategoryAndUser(category, user);

        return optionalBookmark.isPresent();
    }

    public void addCategoryBookmark(Long categoryId, User user) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        categoryBookmarkRepository.findByCategoryAndUser(category, user).ifPresent(m -> {
            throw new ExistingCategoryDibsException();
        });

        CategoryBookmark categoryBookmark = new CategoryBookmark(category, user);
        categoryBookmarkRepository.save(categoryBookmark);
    }

    public void removeCategoryBookmark(Long categoryId, User user) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        CategoryBookmark categoryBookmark = categoryBookmarkRepository.findByCategoryAndUser(category, user).orElseThrow(NoSuchElementException::new);
        categoryBookmarkRepository.delete(categoryBookmark);
    }



}
