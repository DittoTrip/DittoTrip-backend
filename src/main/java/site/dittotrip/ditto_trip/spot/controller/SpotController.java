package site.dittotrip.ditto_trip.spot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.spot.domain.dto.*;
import site.dittotrip.ditto_trip.spot.service.SpotBookmarkService;
import site.dittotrip.ditto_trip.spot.service.SpotService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.*;

/**
 * 1. Spot 리스트 지도 기반 조회 (카테고리 선택 후)
 * 2. 즐겨찾는 Spot 리스트 조회
 * 3. Spot 리스트 검색 조회
 * 4. 방문 spot 리스트 조회
 * 5. Spot 상세 조회
 */
@RestController
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;
    private final SpotBookmarkService spotBookmarkService;

    @GetMapping("/category/{categoryId}/spot/list")
    @Operation(summary = "카테고리 스팟 리스트 조회 (카테고리 선택 후)",
            description = "")
    public SpotCategoryListRes spotListInCategory(@PathVariable(name = "categoryId") Long categoryId,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @RequestParam(name = "userX") Double userX, @RequestParam(name = "userY") Double userY,
                                                  Pageable pageable) {
        User user = getUserFromUserDetails(userDetails, false);
        return spotService.findSpotListInCategory(user, categoryId, userX, userY, pageable);
    }


    @GetMapping("/category/{categoryId}/spot/list/map")
    @Operation(summary = "지도기반 스팟 리스트 조회 (카테고리 선택 후)",
            description = "왼쪽 아래 좌표(start)와 오른쪽 위(end) 좌표를 쿼리로 주세요.")
    public SpotCategoryListRes spotListInMap(@PathVariable(name = "categoryId") Long categoryId,
                                             @AuthenticationPrincipal CustomUserDetails userDetails,
                                             @RequestParam(name = "userX") Double userX, @RequestParam(name = "userY") Double userY,
                                             @RequestParam(name = "startX") Double startX, @RequestParam(name = "endX") Double endX,
                                             @RequestParam(name = "startY") Double startY, @RequestParam(name = "endY") Double endY) {
        User user = getUserFromUserDetails(userDetails, false);
        return spotService.findSpotListInMap(categoryId, user, userX, userY, startX, endX, startY, endY);
    }

    @GetMapping("/spot/list/bookmark")
    @Operation(summary = "내 북마크 스팟 리스트 조회",
            description = "")
    public SpotListRes spotListByBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestParam(name = "userX") Double userX, @RequestParam(name = "userY") Double userY) {
        User user = getUserFromUserDetails(userDetails, true);
        return spotService.findSpotListByBookmark(user, userX, userY);
    }

    @GetMapping("/spot/list/search")
    @Operation(summary = "스팟 리스트 검색 조회",
            description = "")
    public SpotListRes spotListBySearch(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(name = "query") String query,
                                        @RequestParam(name = "userX") Double userX, @RequestParam(name = "userY") Double userY,
                                        Pageable pageable) {
        User user = getUserFromUserDetails(userDetails, false);
        return spotService.findSpotListBySearch(user, query, userX, userY, pageable);
    }

    /**
     * 마이페이지 기능인가 ?
     */
    @GetMapping("/user/{userId}/visited")
    @Operation(summary = "한 유저의 방문 스팟 리스트 조회",
            description = "")
    public SpotVisitListRes spotVisitList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @PathVariable(name = "userId") Long userId,
                                          Pageable pageable) {
        User reqUser = getUserFromUserDetails(userDetails, false);
        return spotService.findSpotVisitList(userId, reqUser, pageable);
    }

    @GetMapping("/spot/{spotId}")
    @Operation(summary = "스팟 상세 조회",
            description = "")
    public SpotDetailRes spotDetail(@PathVariable(name = "spotId") Long spotId,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, false);
        return spotService.findSpotDetail(spotId, user);
    }

    @PostMapping("/spot/{spotId}")
    @Operation(summary = "스팟 방문",
            description = "params \n" +
                    "userX : 경도 \n" +
                    "userY : 위도")
    public void SpotVisit(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @PathVariable(name = "spotId") Long spotId,
                          @RequestParam(name = "userX") Double userX,
                          @RequestParam(name = "userY") Double userY) {
        User user = getUserFromUserDetails(userDetails, true);
        spotService.visitSpot(user, spotId, userX, userY);
    }

    /**
     * SpotBookmark
     */

    @GetMapping("/spot/{spotId}/bookmark")
    @Operation(summary = "스팟 북마크 조회",
            description = "boolean 데이터 반환")
    public Boolean spotBookmarkGet(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable(name = "spotId") Long spotId) {
        User user = getUserFromUserDetails(userDetails, true);
        return spotBookmarkService.findSpotBookmark(user, spotId);
    }


    @PostMapping("/spot/{spotId}/bookmark")
    @Operation(summary = "스팟 북마크 추가",
            description = "")
    public void spotBookmarkAdd(@PathVariable(name = "spotId") Long spotId,
                                @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        spotBookmarkService.addSpotBookmark(spotId, user);
    }

    @DeleteMapping("/spot/{spotId}/bookmark/{bookmarkId}")
    @Operation(summary = "스팟 북마크 삭제",
            description = "")
    public void spotBookmarkRemove(@PathVariable(name = "bookmarkId") Long bookmarkId,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        spotBookmarkService.removeSpotBookmark(bookmarkId, user);
    }

}
