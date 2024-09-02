package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.spot.domain.SpotImage;

public interface SpotImageRepository extends JpaRepository<SpotImage, Long> {
}
