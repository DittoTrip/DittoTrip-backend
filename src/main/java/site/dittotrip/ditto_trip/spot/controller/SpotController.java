package site.dittotrip.ditto_trip.spot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.spot.service.SpotService;

@RestController
@RequestMapping("/spot")
@RequiredArgsConstructor
public class SpotController {

    private final SpotService spotService;

//    @GetMapping("/{spotId}")
//    private SpotDetailRes categoryDetail(@PathVariable(name = "spotId") Long spotId) {
//        return spotDetailService.findSpotDetail(spotId);
//    }

}
