package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.user.domain.User;

public interface SpotVisitRepository extends JpaRepository<SpotVisit, Long> {

    Page<SpotVisit> findByUser(User user, Pageable pageable);

}
