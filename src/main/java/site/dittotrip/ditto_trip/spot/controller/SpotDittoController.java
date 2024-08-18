package site.dittotrip.ditto_trip.spot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.spot.service.SpotDittoService;

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
