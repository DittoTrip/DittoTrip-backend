package site.dittotrip.ditto_trip.reward.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.reward.domain.UserBadge;
import site.dittotrip.ditto_trip.reward.domain.UserItem;
import site.dittotrip.ditto_trip.reward.domain.dto.UserBadgeListRes;
import site.dittotrip.ditto_trip.reward.domain.dto.UserItemListRes;
import site.dittotrip.ditto_trip.reward.repository.UserBadgeRepository;
import site.dittotrip.ditto_trip.reward.repository.UserItemRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RewardService {

    private final UserRepository userRepository;
    private final UserItemRepository userItemRepository;
    private final UserBadgeRepository userBadgeRepository;

    public UserItemListRes findUsersItemList(User user) {
        List<UserItem> userItems = userItemRepository.findByUser(user);

        return UserItemListRes.fromEntities(userItems);
    }

    public UserBadgeListRes findUsersBadgeList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);

        return UserBadgeListRes.fromEntities(userBadges);
    }

}
