package site.dittotrip.ditto_trip.spot.spotditto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.spotditto.domain.SpotDitto;
import site.dittotrip.ditto_trip.spot.spotditto.exception.ExistingSpotDittoException;
import site.dittotrip.ditto_trip.spot.spotditto.repository.SpotDittoRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class SpotDittoAddService {

    private final SpotDittoRepository spotDittoRepository;

    private void addSpotDitto(Spot spot, User user) {

        spotDittoRepository.findBySpotAndUser(spot, user).ifPresent(m -> {
            throw new ExistingSpotDittoException();
        });

        SpotDitto spotDitto = new SpotDitto(spot, user);
        spotDittoRepository.save(spotDitto);
    }

}
