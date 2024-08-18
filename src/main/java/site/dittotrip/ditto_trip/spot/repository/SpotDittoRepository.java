package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotDitto;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.Optional;

public interface SpotDittoRepository extends JpaRepository<SpotDitto, Long> {

    Optional<SpotDitto> findBySpotAndUser(Spot spot, User user);

}
