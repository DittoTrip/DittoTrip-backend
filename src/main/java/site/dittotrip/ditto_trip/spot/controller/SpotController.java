package site.dittotrip.ditto_trip.spot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.spot.domain.dto.detail.SpotDetailRes;
import site.dittotrip.ditto_trip.spot.service.SpotDetailService;

@RestController
@RequestMapping("/spot")
@RequiredArgsConstructor
public class SpotController {

    private final SpotDetailService spotDetailService;

    @GetMapping("/{spotId}")
    private SpotDetailRes categoryDetail(@PathVariable(name = "spotId") Long spotId) {
        return spotDetailService.findSpotDetail(spotId);
    }

}
