package site.dittotrip.ditto_trip.spot.spotditto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.spot.spotditto.service.SpotDittoAddService;
import site.dittotrip.ditto_trip.spot.spotditto.service.SpotDittoRemoveService;

@RestController
@RequestMapping("/spot/{spotId}/spot-ditto")
@RequiredArgsConstructor
public class SpotDittoController {

    private final SpotDittoAddService spotDittoAddService;
    private final SpotDittoRemoveService spotDittoRemoveService;

    /**
     * SpotDitto 추가
     */
//    @PostMapping

    /**
     * SpotDitto 삭제
     */
//    @DeleteMapping("/{spotDittoId}")

}
