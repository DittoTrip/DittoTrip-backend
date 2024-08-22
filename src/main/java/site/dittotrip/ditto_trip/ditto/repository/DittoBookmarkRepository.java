package site.dittotrip.ditto_trip.ditto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoBookmark;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.Optional;

public interface DittoBookmarkRepository extends JpaRepository<DittoBookmark, Long> {

    Optional<DittoBookmark> findByDittoAndUser(Ditto ditto, User user);

}
