package site.dittotrip.ditto_trip.reward.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.reward.domain.UserReward;
import site.dittotrip.ditto_trip.reward.domain.dto.UserBadgeListRes;
import site.dittotrip.ditto_trip.reward.domain.dto.UserItemListRes;
import site.dittotrip.ditto_trip.reward.repository.UserRewardRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RewardService {

    private final UserRepository userRepository;
    private final UserRewardRepository userRewardRepository;

    public UserItemListRes findUsersItemList(Long reqUserId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        List<UserReward> userRewards = userRewardRepository.findUserItemByUser(reqUser);
        return UserItemListRes.fromEntities(userRewards);
    }

    public UserBadgeListRes findUsersBadgeList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<UserReward> userRewards = userRewardRepository.findUserBadgeByUser(user);
        return UserBadgeListRes.fromEntities(userRewards);
    }

}
