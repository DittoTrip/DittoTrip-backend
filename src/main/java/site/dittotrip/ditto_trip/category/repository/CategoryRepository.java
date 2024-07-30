package site.dittotrip.ditto_trip.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.category.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
