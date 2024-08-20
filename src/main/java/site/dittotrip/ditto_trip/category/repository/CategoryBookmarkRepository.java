package site.dittotrip.ditto_trip.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.Optional;

public interface CategoryBookmarkRepository extends JpaRepository<CategoryBookmark, Long> {

    Optional<CategoryBookmark> findByCategoryAndUser(Category category, User user);

}
