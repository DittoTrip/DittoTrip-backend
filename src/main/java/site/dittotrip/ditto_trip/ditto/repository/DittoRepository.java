package site.dittotrip.ditto_trip.ditto.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface DittoRepository extends JpaRepository<Ditto, Long> {

    @EntityGraph(attributePaths = "user")
    @Query("select d from Ditto d where d.id= :dittoId")
    Optional<Ditto> findByIdWithUser(Long dittoId);

    List<Ditto> findTop6ByUserOrderByCreatedDateTimeDesc(User user);
}
