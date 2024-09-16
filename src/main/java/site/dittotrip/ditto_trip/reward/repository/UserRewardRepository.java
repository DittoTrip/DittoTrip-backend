package site.dittotrip.ditto_trip.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.dittotrip.ditto_trip.reward.domain.UserReward;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * 삭제 예정
 */
public interface UserRewardRepository extends JpaRepository<UserReward, Long> {

//    List<UserReward> findByUser(User user);
//
//    @Query("select ur from UserReward ur where ur.id= :id and ur.reward.rewardType= :rewardType")
//    Optional<UserReward> findByIdAndRewardType(Long id, RewardType rewardType);
//
//    @Query("select ur from UserReward ur where ur.user= :user and ur.reward.rewardType= 'ITEM'")
//    List<UserReward> findUserItemByUser(User user);
//
//    @Query("select ur from UserReward ur where ur.user= :user and ur.reward.rewardType= 'BADGE'")
//    List<UserReward> findUserBadgeByUser(User user);

}
