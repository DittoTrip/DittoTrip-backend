package site.dittotrip.ditto_trip.spot.spotditto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.spotditto.domain.SpotDitto;
import site.dittotrip.ditto_trip.spot.spotditto.repository.SpotDittoRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class SpotDittoRemoveService {

    private final SpotDittoRepository spotDittoRepository;

    public void removeSpotDitto(Spot spot, User user) {
        SpotDitto spotDitto = spotDittoRepository.findBySpotAndUser(spot, user).orElseThrow(NoSuchElementException::new);
        spotDittoRepository.delete(spotDitto);
    }

}
