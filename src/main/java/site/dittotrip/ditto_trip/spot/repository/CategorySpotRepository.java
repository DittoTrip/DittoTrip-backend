package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;

import java.util.List;

public interface CategorySpotRepository extends JpaRepository<CategorySpot, Long> {

    /**
     * 미완성
     */
    @Query("select cs from CategorySpot cs where cs.category= :category")
    List<CategorySpot> findByScope(@Param("category") Category category);

}
