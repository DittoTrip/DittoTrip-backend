package site.dittotrip.ditto_trip.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.reward.domain.Reward;

public interface RewardRepository extends JpaRepository<Reward, Long> {
}
