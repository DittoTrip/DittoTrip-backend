package site.dittotrip.ditto_trip.ditto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.ditto.domain.DittoImage;

public interface DittoImageRepository extends JpaRepository<DittoImage, Long> {

}
