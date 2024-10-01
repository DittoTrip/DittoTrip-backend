package site.dittotrip.ditto_trip.quest.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.utils.UserLevelConverter;
import site.dittotrip.ditto_trip.reward.domain.Badge;
import site.dittotrip.ditto_trip.reward.domain.UserBadge;
import site.dittotrip.ditto_trip.reward.repository.BadgeRepository;
import site.dittotrip.ditto_trip.reward.repository.UserBadgeRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
@Transactional
public class ExpManager {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserBadgeRepository userBadgeRepository;

    private final Map<String, Integer> EXP_MAP = Map.of(
            "visitSpot", 50,
            "saveReview", 100,
            "saveSpotApply", 100
    );

    public void handleExp(CustomUserDetails userDetails, String methodName) {
        Long reqUserId = CustomUserDetails.getUserIdFromUserDetails(userDetails, true);
        User user = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);

        UserProfile userProfile = user.getUserProfile();

        int beforeLevel = UserLevelConverter.getLevel(user.getUserProfile().getProgressionBar());
        userProfile.addExp(EXP_MAP.get(methodName));
        int afterLevel = UserLevelConverter.getLevel(userProfile.getProgressionBar());

        if (beforeLevel < afterLevel) {
            String badgeName = UserLevelConverter.REWARD_MAP.get(afterLevel);
            Badge rewardBadge = badgeRepository.findByName(badgeName).orElseThrow(NoSuchElementException::new);
            userBadgeRepository.save(new UserBadge(user, rewardBadge));
        }
    }

}
