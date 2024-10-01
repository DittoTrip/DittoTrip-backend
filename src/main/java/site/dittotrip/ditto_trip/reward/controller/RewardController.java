package site.dittotrip.ditto_trip.reward.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.reward.domain.dto.*;
import site.dittotrip.ditto_trip.reward.service.RewardService;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

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
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return rewardService.findUsersItemList(reqUserId);
    }

    @GetMapping("/user/{userId}/badge/list")
    @Operation(summary = "유저의 뱃지 리스트 조회",
            description = "")
    public UserBadgeListRes usersBadgeListRes(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @PathVariable(name = "userId") Long userId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return rewardService.findBadgeList(reqUserId, userId);
    }

    @GetMapping("/item/list/admin")
    @Operation(summary = "전체 아이템 리스트 조회 (관리자 기능)",
            description = "")
    public ItemListRes itemList() {
        return rewardService.findItemList();
    }

    @PostMapping("/badge")
    @Operation(summary = "뱃지 등록 (관리자 기능)",
            description = "")
    public void badgeSave(@RequestPart(name = "saveReq") BadgeSaveReq saveReq,
                          @RequestPart(name = "image") MultipartFile multipartFile) {
        rewardService.saveBadge(saveReq, multipartFile);
    }

    @PostMapping("/item")
    @Operation(summary = "아이템 등록 (관리자 기능)",
            description = "")
    public void itemSave(@RequestPart(name = "saveReq") ItemSaveReq saveReq,
                          @RequestPart(name = "image") MultipartFile multipartFile,
                          @RequestPart(name = "wearingImage") MultipartFile multipartFile2) {
        rewardService.saveItem(saveReq, multipartFile, multipartFile2);
    }

}