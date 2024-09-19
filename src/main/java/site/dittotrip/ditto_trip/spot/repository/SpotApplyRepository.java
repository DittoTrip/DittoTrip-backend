package site.dittotrip.ditto_trip.spot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

public interface SpotApplyRepository extends JpaRepository<SpotApply, Long> {

    List<SpotApply> findByUser(User user);

    Page<SpotApply> findByNameContaining(String word, Pageable pageable);

}
