package site.dittotrip.ditto_trip.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.reward.domain.UserReward;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

public interface UserRewardRepository extends JpaRepository<UserReward, Long> {

    List<UserReward> findByUser(User user);

    @Query("select ur from UserReward ur where ur.user= :user and ur.reward.rewardType= 'ITEM'")
    List<UserReward> findUserItemByUser(User user);

    @Query("select ur from UserReward ur where ur.user= :user and ur.reward.rewardType= 'BADGE'")
    List<UserReward> findUserBadgeByUser(User user);

}
