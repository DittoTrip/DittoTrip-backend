package site.dittotrip.ditto_trip.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.reward.domain.UserReward;

public interface UserRewardRepository extends JpaRepository<UserReward, Long> {


}
