package site.dittotrip.ditto_trip.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.follow.domain.dto.FollowListRes;
import site.dittotrip.ditto_trip.follow.service.FollowService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.service.CustomUserDetails.getUserFromUserDetails;

/**
 * 1. 팔로잉 리스트 조회
 * 2. 팔로잉 리스트 조회
 * 3. 팔로우 등록
 * 4. 팔로우 취소
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/following-list/{userId}")
    public FollowListRes FollowingList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable(name = "userId") Long userId) {
        User user = getUserFromUserDetails(userDetails, true);
        return followService.findFollowingList(user, userId);
    }

    @GetMapping("/followed-list/{userId}")
    public FollowListRes followedList(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable(name = "userId") Long userId) {
        User user = getUserFromUserDetails(userDetails, true);
        return followService.findFollowedList(user, userId);
    }

    @PostMapping("/follow/{userId}")
    public void followSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @PathVariable(name = "userId") Long userId) {
        User user = getUserFromUserDetails(userDetails, true);
        followService.saveFollow(user, userId);
    }

    @DeleteMapping("/follow/{followId}")
    public void followRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable(name = "followId") Long followId) {
        User user = getUserFromUserDetails(userDetails, true);
        followService.removeFollow(user, followId);
    }

}
