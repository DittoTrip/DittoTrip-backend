package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotDitto;
import site.dittotrip.ditto_trip.spot.exception.ExistingSpotDittoException;
import site.dittotrip.ditto_trip.spot.repository.SpotDittoRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class SpotDittoService {

    private final SpotDittoRepository spotDittoRepository;

    private void addSpotDitto(Spot spot, User user) {

        spotDittoRepository.findBySpotAndUser(spot, user).ifPresent(m -> {
            throw new ExistingSpotDittoException();
        });

        SpotDitto spotDitto = new SpotDitto(spot, user);
        spotDittoRepository.save(spotDitto);
    }

    public void removeSpotDitto(Spot spot, User user) {
        SpotDitto spotDitto = spotDittoRepository.findBySpotAndUser(spot, user).orElseThrow(NoSuchElementException::new);
        spotDittoRepository.delete(spotDitto);
    }

}
