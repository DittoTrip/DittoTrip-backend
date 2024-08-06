package site.dittotrip.ditto_trip.spot.categoryspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.spot.categoryspot.domain.CategorySpot;

import java.util.List;

public interface CategorySpotRepository extends JpaRepository<CategorySpot, Long> {

    /**
     * 미완성
     */
    List<CategorySpot> findByScope();

}
