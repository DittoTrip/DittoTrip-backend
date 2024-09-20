package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.exception.ExistingCategoryBookmarkException;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 작업 중
 */
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CategoryBookmarkService {

    private final UserRepository userRepository;
    private final CategoryBookmarkRepository categoryBookmarkRepository;
    private final CategoryRepository categoryRepository;

    public Boolean findCategoryBookmark(Long reqUserId, Long categoryId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        Optional<CategoryBookmark> optionalBookmark = categoryBookmarkRepository.findByCategoryAndUser(category, reqUser);

        return optionalBookmark.isPresent();
    }

    public void addCategoryBookmark(Long reqUserId, Long categoryId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        categoryBookmarkRepository.findByCategoryAndUser(category, reqUser).ifPresent(m -> {
            throw new ExistingCategoryBookmarkException();
        });

        CategoryBookmark categoryBookmark = new CategoryBookmark(category, reqUser);
        categoryBookmarkRepository.save(categoryBookmark);
    }

    public void removeCategoryBookmark(Long reqUserId, Long categoryId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);

        CategoryBookmark categoryBookmark = categoryBookmarkRepository.findByCategoryAndUser(category, reqUser).orElseThrow(NoSuchElementException::new);
        categoryBookmarkRepository.delete(categoryBookmark);
    }

}
