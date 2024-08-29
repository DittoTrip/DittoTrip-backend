package site.dittotrip.ditto_trip.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.item.domain.dto.UserBadgeListRes;
import site.dittotrip.ditto_trip.item.service.BadgeService;

/**
 * 1. 유저의 보유 아이템 리스트 조회
 */
@RestController
@RequestMapping("/user/{userId}/badge")
@RequiredArgsConstructor
public class BadgeController {

    private final BadgeService badgeService;

    @GetMapping("/list")
    public UserBadgeListRes UsersItemList(@PathVariable(name = "userId") Long userId) {
        return badgeService.findUsersItemList(userId);
    }

}
