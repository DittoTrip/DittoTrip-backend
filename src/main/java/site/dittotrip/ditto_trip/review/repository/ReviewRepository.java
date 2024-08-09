package site.dittotrip.ditto_trip.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findTop3BySpotOrderByCreatedDateTimeDesc(Spot spot);

    List<Review> findBySpotOrderByCreatedDateTimeDesc(Spot spot);

}
