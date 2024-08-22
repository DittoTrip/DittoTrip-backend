package site.dittotrip.ditto_trip.ditto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.ditto.domain.DittoBookmark;

public interface DittoBookmarkRepository extends JpaRepository<DittoBookmark, Long> {

}
