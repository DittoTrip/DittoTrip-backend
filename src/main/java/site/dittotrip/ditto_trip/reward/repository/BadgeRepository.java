package site.dittotrip.ditto_trip.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.reward.domain.Badge;

import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

  Badge findBadgeByNameEquals(String name);
  Optional<Badge> findByName(String name);
}
