package site.dittotrip.ditto_trip.spot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.spot.service.SpotBookmarkService;

@RestController
@RequestMapping("/spot/{spotId}/spot-ditto")
@RequiredArgsConstructor
public class SpotBookmarkController {

    private final SpotBookmarkService spotBookmarkService;

    /**
     * SpotDitto 추가
     */
//    @PostMapping

    /**
     * SpotDitto 삭제
     */
//    @DeleteMapping("/{spotDittoId}")

}
