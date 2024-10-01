package site.dittotrip.ditto_trip.quest.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.Map;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
@Transactional
public class ExpManager {

    private final UserRepository userRepository;

    private final Map<String, Integer> EXP_MAP = Map.of(
            "visitSpot", 50,
            "saveReview", 100,
            "saveSpotApply", 100
    );

    public void handleExp(CustomUserDetails userDetails, String methodName) {
        Long reqUserId = CustomUserDetails.getUserIdFromUserDetails(userDetails, true);
        User user = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);

        UserProfile userProfile = user.getUserProfile();
        userProfile.addExp(EXP_MAP.get(methodName));
    }

}
