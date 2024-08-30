package site.dittotrip.ditto_trip.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.item.domain.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
}
