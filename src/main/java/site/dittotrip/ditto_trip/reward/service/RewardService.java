package site.dittotrip.ditto_trip.reward.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.reward.domain.Badge;
import site.dittotrip.ditto_trip.reward.domain.Reward;
import site.dittotrip.ditto_trip.reward.domain.UserBadge;
import site.dittotrip.ditto_trip.reward.domain.UserItem;
import site.dittotrip.ditto_trip.reward.domain.dto.UserBadgeListRes;
import site.dittotrip.ditto_trip.reward.domain.dto.UserItemListRes;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;
import site.dittotrip.ditto_trip.reward.repository.*;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RewardService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserItemRepository userItemRepository;
    private final UserBadgeRepository userBadgeRepository;

    public UserItemListRes findUsersItemList(Long reqUserId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        List<UserItem> userItems = userItemRepository.findByUser(reqUser);
        return UserItemListRes.fromEntities(reqUser, userItems);
    }

    public UserBadgeListRes findBadgeList(Long reqUserId, Long userId) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        List<Badge> badges = badgeRepository.findAll();
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);

        if (reqUser.equals(user)) {
            Map<Reward, UserBadge> ownBadgeMap = new HashMap<>();
            for (UserBadge userBadge : userBadges) {
                ownBadgeMap.put(userBadge.getBadge(), userBadge);
            }
            return UserBadgeListRes.fromEntitiesAtMine(user, badges, ownBadgeMap);
        } else {
            return UserBadgeListRes.fromEntities(badges);
        }
    }

}
