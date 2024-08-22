package site.dittotrip.ditto_trip.ditto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;

public interface DittoRepository extends JpaRepository<Ditto, Long> {

}
