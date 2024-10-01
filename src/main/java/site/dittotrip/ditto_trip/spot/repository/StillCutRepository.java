package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotImage;

import java.util.List;

public interface StillCutRepository extends JpaRepository<SpotImage, Long> {

    List<SpotImage> findTop3BySpotOrderByCreatedDateTimeDesc(Spot spot);

    List<SpotImage> findBySpot(Spot spot);

}
