package site.dittotrip.ditto_trip.quest.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional
public class ExpManager {

    private final Map<String, Integer> expMap = Map.of(
            "visitSpot", 50,
            "saveReview", 100
    );

    public void handleExp(CustomUserDetails userDetails, String methodName) {
        User user = CustomUserDetails.getUserFromUserDetails(userDetails, true);

        UserProfile userProfile = user.getUserProfile();
        userProfile.addExp(expMap.get(methodName));
    }

}
