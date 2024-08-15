package site.dittotrip.ditto_trip.spot.spotditto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.spot.spotditto.service.SpotDittoService;

@RestController
@RequestMapping("/spot/{spotId}/spot-ditto")
@RequiredArgsConstructor
public class SpotDittoController {

    private final SpotDittoService spotDittoService;

    /**
     * SpotDitto 추가
     */
//    @PostMapping

    /**
     * SpotDitto 삭제
     */
//    @DeleteMapping("/{spotDittoId}")

}
