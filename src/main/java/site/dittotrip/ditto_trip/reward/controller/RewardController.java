package site.dittotrip.ditto_trip.reward.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.reward.domain.dto.UserBadgeListRes;
import site.dittotrip.ditto_trip.reward.domain.dto.UserItemListRes;
import site.dittotrip.ditto_trip.reward.service.RewardService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserFromUserDetails;

/**
 * 1. 유저의 보유 아이템 리스트 조회 (자신의 아이템 리스트만 조회 가능 (수정 시))
 * 2. 유저의 보유 뱃지 리스트 조회 (타 유저도 가능)
 */
@RestController
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    @GetMapping("/item/list")
    @Operation(summary = "내 아이템 리스트 조회 (프로필 아이템)",
            description = "")
    public UserItemListRes usersItemList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        return rewardService.findUsersItemList(user);
    }

    @GetMapping("/user/{userId}/badge/list")
    @Operation(summary = "유저의 뱃지 리스트 조회",
            description = "")
    public UserBadgeListRes usersBadgeListRes(@PathVariable(name = "userId") Long userId) {
        return rewardService.findUsersBadgeList(userId);
    }

}