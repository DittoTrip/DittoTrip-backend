package site.dittotrip.ditto_trip.userpage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.swagger.SwaggerAuth;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.userpage.domain.dto.UserPageRes;
import site.dittotrip.ditto_trip.userpage.service.UserPageService;

/**
 * 타인의 유저 페이지와 내 유저 페이지 조회의 데이터가 다를 경우 수정 필요
 * 1. 유저 페이지 조회
 */
@RestController
@RequestMapping("/user/{userId}/user-page")
@RequiredArgsConstructor
public class UserPageController {

    private final UserPageService userPageService;

    @GetMapping
    @SwaggerAuth
    public UserPageRes userPageFind(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable(name = "userId") Long userId) {
        User reqUser = userDetails.getUser();
        return userPageService.findUserPage(reqUser, userId);
    }

}
