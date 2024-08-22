package site.dittotrip.ditto_trip.spot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.spot.service.SpotBookmarkService;
import site.dittotrip.ditto_trip.spot.service.SpotService;
import site.dittotrip.ditto_trip.user.domain.User;

@RestController
@RequestMapping("/spot")
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;
    private final SpotBookmarkService spotBookmarkService;

//    @GetMapping("/{spotId}")
//    private SpotDetailRes categoryDetail(@PathVariable(name = "spotId") Long spotId) {
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
