package site.dittotrip.ditto_trip.spot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spot")
@RequiredArgsConstructor
public class SpotController {


    @GetMapping("/{spotId}")
    private void categoryDetail(@PathVariable(name = "spotId") Long spotId) {

    }

}
