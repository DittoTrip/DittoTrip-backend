package site.dittotrip.ditto_trip.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.user.domain.dto.ContentListRes;
import site.dittotrip.ditto_trip.user.domain.dto.UserDetailRes;
import site.dittotrip.ditto_trip.user.domain.dto.UserListForAdminRes;
import site.dittotrip.ditto_trip.user.domain.dto.UserListRes;
import site.dittotrip.ditto_trip.user.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list/search")
    @Operation(summary = "유저 리스트 검색 조회",
            description = "")
    public UserListRes userList(@RequestParam(name = "query") String query,
                                Pageable pageable) {
        return userService.findUserList(query, pageable);
    }

    @GetMapping("/list/search/admin")
    @Operation(summary = "유저 리스트 검색 조회 (관리자용)",
            description = "query가 없는 경우 전체 조회")
    public UserListForAdminRes userListForAdmin(@RequestParam(name = "query", required = false) String query,
                                                Pageable pageable) {
        return userService.findUserListForAdmin(query, pageable);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "유저 상세 조회 (관리자용)",
            description = "")
    public UserDetailRes userDetailForAdmin(@PathVariable(name = "userId") Long userId) {
        return userService.findUserDetail(userId);
    }

    @GetMapping("/my-info")
    public UserDetailRes myUserInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqUserId = CustomUserDetails.getUserIdFromUserDetails(userDetails, true);
        return userService.findMyUserInfo(reqUserId);
    }

    @GetMapping("/{userId}/review/list")
    @Operation(summary = "유저 상세 페이지에서 리뷰 리스트 조회 (관리자용)",
            description = "")
    public ContentListRes usersReviewList(@PathVariable(name = "userId") Long userId,
                                          Pageable pageable) {
        return userService.findUsersReviewList(userId, pageable);
    }

    @GetMapping("/{userId}/ditto/list")
    @Operation(summary = "유저 상세 페이지에서 디토 리스트 조회 (관리자용)",
            description = "")
    public ContentListRes usersDittoList(@PathVariable(name = "userId") Long userId,
                                          Pageable pageable) {
        return userService.findUsersDittoList(userId, pageable);
    }


    @PutMapping("/{userId}")
    @Operation(summary = "유저 활동 일지 정지 (관리자용)",
            description = "")
    public void userPause(@PathVariable(name = "userId") Long userId) {
        userService.pauseUser(userId);
    }

}
