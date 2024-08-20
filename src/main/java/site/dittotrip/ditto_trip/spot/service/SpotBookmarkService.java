package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;
import site.dittotrip.ditto_trip.spot.exception.ExistingSpotDittoException;
import site.dittotrip.ditto_trip.spot.repository.SpotDittoRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class SpotBookmarkService {

    private final SpotDittoRepository spotDittoRepository;

    private void addSpotDitto(Spot spot, User user) {

        spotDittoRepository.findBySpotAndUser(spot, user).ifPresent(m -> {
            throw new ExistingSpotDittoException();
        });

        SpotBookmark spotBookmark = new SpotBookmark(spot, user);
        spotDittoRepository.save(spotBookmark);
    }

    public void removeSpotDitto(Spot spot, User user) {
        SpotBookmark spotBookmark = spotDittoRepository.findBySpotAndUser(spot, user).orElseThrow(NoSuchElementException::new);
        spotDittoRepository.delete(spotBookmark);
    }

}
