package site.dittotrip.ditto_trip.userpage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
