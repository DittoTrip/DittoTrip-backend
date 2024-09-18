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
            description = "최신순 (createdDateTime,desc) | 평점순 (rating,desc) | 거리순 (distance) " +
                    "(거리순 조회의 경우 유저 위치 정보가 없을 경우 예외가 발생합니다.)")
    public SpotCategoryListRes spotListInCategory(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                  @PathVariable(name = "categoryId") Long categoryId,
                                                  @RequestParam(name = "userX", required = false) Double userX,
                                                  @RequestParam(name = "userY", required = false) Double userY,
                                                  Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return spotService.findSpotListInCategory(reqUserId, categoryId, userX, userY, pageable);
    }

    @GetMapping("/category/{categoryId}/spot/list/map")
    @Operation(summary = "지도기반 스팟 리스트 조회 (카테고리 선택 후)",
            description = "왼쪽 아래 좌표(start)와 오른쪽 위(end) 좌표를 쿼리로 주세요.")
    public SpotCategoryListRes spotListInMap(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable(name = "categoryId") Long categoryId,
                                             @RequestParam(name = "userX", required = false) Double userX,
                                             @RequestParam(name = "userY", required = false) Double userY,
                                             @RequestParam(name = "startX") Double startX,
                                             @RequestParam(name = "endX") Double endX,
                                             @RequestParam(name = "startY") Double startY,
                                             @RequestParam(name = "endY") Double endY) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return spotService.findSpotListInMap(reqUserId, categoryId, userX, userY, startX, endX, startY, endY);
    }

    @GetMapping("/spot/list/bookmark")
    @Operation(summary = "내 북마크 스팟 리스트 조회",
            description = "")
    public SpotListRes spotListByBookmark(@AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestParam(name = "userX", required = false) Double userX,
                                          @RequestParam(name = "userY", required = false) Double userY) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return spotService.findSpotListByBookmark(reqUserId, userX, userY);
    }

    @GetMapping("/spot/list/search")
    @Operation(summary = "스팟 리스트 검색 조회 (관리자도 사용)",
            description = "")
    public SpotListRes spotListBySearch(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(name = "query", required = false) String query,
                                        @RequestParam(name = "userX", required = false) Double userX,
                                        @RequestParam(name = "userY", required = false) Double userY,
                                        Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return spotService.findSpotListBySearch(reqUserId, query, userX, userY, pageable);
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
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return spotService.findSpotVisitList(reqUserId, userId, pageable);
    }

    @GetMapping("/spot/{spotId}")
    @Operation(summary = "스팟 상세 조회",
            description = "")
    public SpotDetailRes spotDetail(@PathVariable(name = "spotId") Long spotId,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return spotService.findSpotDetail(reqUserId, spotId);
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
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        spotService.visitSpot(reqUserId, spotId, userX, userY);
    }

    /**
     * SpotBookmark
     */
    @GetMapping("/spot/{spotId}/bookmark")
    @Operation(summary = "스팟 북마크 조회",
            description = "boolean 데이터 반환")
    public Boolean spotBookmarkGet(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "spotId") Long spotId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return spotBookmarkService.findSpotBookmark(reqUserId, spotId);
    }


    @PostMapping("/spot/{spotId}/bookmark")
    @Operation(summary = "스팟 북마크 추가",
            description = "")
    public void spotBookmarkAdd(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable(name = "spotId") Long spotId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        spotBookmarkService.addSpotBookmark(reqUserId, spotId);
    }

    @DeleteMapping("/spot/{spotId}/bookmark/{bookmarkId}")
    @Operation(summary = "스팟 북마크 삭제",
            description = "")
    public void spotBookmarkRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "bookmarkId") Long bookmarkId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        spotBookmarkService.removeSpotBookmark(reqUserId, bookmarkId);
    }

}
