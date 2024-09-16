package site.dittotrip.ditto_trip.profile.contoller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.profile.domain.dto.UserBadgeModifyReq;
import site.dittotrip.ditto_trip.profile.domain.dto.UserNicknameModifyReq;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileModifyReq;
import site.dittotrip.ditto_trip.profile.service.UserProfileService;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

/**
 * 1. 닉네임 수정
 * 2. Item 수정
 * 3. Badge 수정
 */
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PutMapping("/nickname")
    @Operation(summary = "내 닉네임 수정하기",
            description = "")
    public void userNicknameModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @RequestBody UserNicknameModifyReq modifyReq) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        userProfileService.modifyUserNickname(reqUserId, modifyReq);
    }

    @PutMapping("/items")
    @Operation(summary = "내 아이템 수정하기",
            description = "")
    public void userItemModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @RequestBody UserProfileModifyReq modifyReq) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        userProfileService.modifyUserItem(reqUserId, modifyReq);
    }

    @PutMapping("/badge")
    @Operation(summary = "내 뱃지 수정하기",
            description = "")
    public void userBadgeModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @RequestBody UserBadgeModifyReq modifyReq) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        userProfileService.modifyUserBadge(reqUserId, modifyReq);
    }

}
