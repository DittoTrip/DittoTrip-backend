package site.dittotrip.ditto_trip.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findTop3BySpotOrderByCreatedDateTimeDesc(Spot spot);

    List<Review> findBySpotOrderByCreatedDateTimeDesc(Spot spot);

    Long countBySpot(Spot spot);

    Optional<Review> findByUser(User user);

}
