package site.dittotrip.ditto_trip.ditto.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;

import java.util.Optional;

public interface DittoRepository extends JpaRepository<Ditto, Long> {

    @EntityGraph(attributePaths = "{user}")
    @Query("select d from Ditto d where d.id= :dittoId")
    Optional<Ditto> findByIdWithUser(Long dittoId);

}
