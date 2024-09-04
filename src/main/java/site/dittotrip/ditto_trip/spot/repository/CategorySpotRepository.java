package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.List;
import java.util.Optional;

public interface CategorySpotRepository extends JpaRepository<CategorySpot, Long> {

    @EntityGraph(attributePaths = "spot")
    Page<CategorySpot> findByCategory(Category category, Pageable pageable);

    @EntityGraph(attributePaths = "spot")
    @Query("select cs from CategorySpot cs where cs.category= :category" +
            " and :startX < cs.spot.pointX and cs.spot.pointX < :endX" +
            " and :startY < cs.spot.pointY and cs.spot.pointY < :endY")
    List<CategorySpot> findByCategoryInScope(Category category,
                                             Double startX, Double endX,
                                             Double startY, Double endY);

    Optional<CategorySpot> findByCategoryAndSpot(Category category, Spot spot);

}
