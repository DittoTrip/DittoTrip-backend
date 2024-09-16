package site.dittotrip.ditto_trip.profile.contoller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileModifyReq;
import site.dittotrip.ditto_trip.profile.service.UserProfileService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserFromUserDetails;
import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

/**
 * 1. 프로필 수정 - item, badge
 */
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PutMapping
    @Operation(summary = "내 프로필 수정하기",
            description = "")
    public void userProfileModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @RequestBody UserProfileModifyReq modifyReq) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        userProfileService.modifyUserProfile(reqUserId, modifyReq);
    }

}
