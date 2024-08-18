package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.StillCut;

import java.util.List;

public interface StillCutRepository extends JpaRepository<StillCut, Long> {

    List<StillCut> findTop3BySpotOrderByCreatedDateTimeDesc(Spot spot);

}
