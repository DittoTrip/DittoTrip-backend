package site.dittotrip.ditto_trip.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.category.domain.CategoryDibs;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.Optional;

public interface CategoryDibsRepository extends JpaRepository<CategoryDibs, Long> {

    Optional<CategoryDibs> findByCategoryAndUser(Category category, User user);

}
