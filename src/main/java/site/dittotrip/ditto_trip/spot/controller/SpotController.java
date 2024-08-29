package site.dittotrip.ditto_trip.spot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.spot.domain.dto.*;
import site.dittotrip.ditto_trip.spot.service.SpotApplyService;
import site.dittotrip.ditto_trip.spot.service.SpotBookmarkService;
import site.dittotrip.ditto_trip.spot.service.SpotService;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

import static site.dittotrip.ditto_trip.auth.service.CustomUserDetails.*;

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
    private final SpotApplyService spotApplyService;

    @GetMapping("/category/{categoryId}/spot/list")
    @Operation(summary = "지도기반 스팟 리스트 조회 (카테고리 선택 후)",
            description = "왼쪽 아래 좌표(start)와 오른쪽 위(end) 좌표를 쿼리로 주세요.")
    public SpotListInMapRes spotListInMap(@PathVariable(name = "categoryId") Long categoryId,
                                          @AuthenticationPrincipal CustomUserDetails userDetails,
                                          @RequestParam(name = "startX") Double startX, @RequestParam(name = "endX") Double endX,
                                          @RequestParam(name = "startY") Double startY, @RequestParam(name = "endY") Double endY) {
        User user = getUserFromUserDetails(userDetails, false);
        return spotService.findSpotListInMap(categoryId, user, startX, endX, startY, endY);
    }

    @GetMapping("/spot/list/bookmark")
    @Operation(summary = "내 북마크 스팟 리스트 조회",
            description = "")
    public SpotListRes spotListByBookmark(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        return spotService.findSpotListByBookmark(user);
    }

    @GetMapping("/spot/list/search")
    @Operation(summary = "스팟 리스트 검색 조회",
            description = "")
    public SpotListRes spotListBySearch(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(name = "query") String query,
                                        Pageable pageable) {
        User user = getUserFromUserDetails(userDetails, false);
        return spotService.findSpotListBySearch(user, query, pageable);
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

    @PostMapping("/spot/apply")
    @Operation(summary = "스팟 등록 신청",
            description = "image는 대표이미지입니다.")
    public void spotApplySave(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @RequestBody SpotApplySaveReq saveReq,
                              @RequestParam(name = "image") MultipartFile multipartFile,
                              @RequestParam(name = "images") List<MultipartFile> multipartFiles) {
        User user = userDetails.getUser();
        spotApplyService.saveSpotApply(user, saveReq, multipartFile, multipartFiles);
    }

    /**
     * SpotBookmark
     */
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
