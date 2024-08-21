package site.dittotrip.ditto_trip.spot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotListRes;
import site.dittotrip.ditto_trip.spot.service.SpotBookmarkService;
import site.dittotrip.ditto_trip.spot.service.SpotService;
import site.dittotrip.ditto_trip.user.domain.User;

/**
 * 1. Spot 리스트 조회 (카테고리 선택 후 지도 기반)
 * 2. Spot 리스트 검색 조회
 * 3. Spot 상세 조회
 */
@RestController
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;
    private final SpotBookmarkService spotBookmarkService;

    @GetMapping("/category/{categoryId}/spot/list")
    public SpotListRes spotList(@PathVariable(name = "categoryId") Long categoryId,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                @RequestParam(name = "startX") Double startX, @RequestParam(name = "endX") Double endX,
                                @RequestParam(name = "startY") Double startY, @RequestParam(name = "endY") Double endY) {
        User user = customUserDetails.getUser();
        return spotService.findSpotList(categoryId, user, startX, endX, startY, endY);
    }

//    @GetMapping("/{spotId}")
//    public SpotDetailRes categoryDetail(@PathVariable(name = "spotId") Long spotId) {
//        return spotDetailService.findSpotDetail(spotId);
//    }

    @PostMapping("/{spotId}/bookmark")
    public void spotBookmarkAdd(@PathVariable(name = "spotId") Long spotId,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        spotBookmarkService.addSpotBookmark(spotId, user);
    }

    @DeleteMapping("/{spotId}/bookmark")
    public void spotBookmarkRemove(@PathVariable(name = "spotId") Long spotId,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        spotBookmarkService.removeSpotBookmark(spotId, user);
    }

}
