package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface SpotVisitRepository extends JpaRepository<SpotVisit, Long> {

    Page<SpotVisit> findByUser(User user, Pageable pageable);

    @Query("select sv from SpotVisit sv where sv.user= :user and sv.spot= :spot order by sv.createdDateTime desc")
    List<SpotVisit> findByUserAndSpot(User user, Spot spot);

}
