package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;

public interface SpotRepository extends JpaRepository<Spot, Long> {

}
