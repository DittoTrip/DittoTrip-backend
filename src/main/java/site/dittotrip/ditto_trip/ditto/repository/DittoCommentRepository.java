package site.dittotrip.ditto_trip.ditto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;

public interface DittoCommentRepository extends JpaRepository<DittoComment, Long> {

}
