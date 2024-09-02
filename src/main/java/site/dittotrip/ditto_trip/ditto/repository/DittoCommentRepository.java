package site.dittotrip.ditto_trip.ditto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;

import java.util.List;

public interface DittoCommentRepository extends JpaRepository<DittoComment, Long> {

    @Query("select c from DittoComment c where c.ditto= :ditto and c.parentDittoComment is null")
    public List<DittoComment> findParentCommentByDitto(Ditto ditto);

    Long countByDitto(Ditto ditto);

}
