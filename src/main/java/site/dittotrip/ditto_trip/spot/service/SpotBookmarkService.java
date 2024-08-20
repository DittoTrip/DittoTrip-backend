package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;
import site.dittotrip.ditto_trip.spot.exception.ExistingSpotDittoException;
import site.dittotrip.ditto_trip.spot.repository.SpotBookmarkRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class SpotBookmarkService {

    private final SpotRepository spotRepository;
    private final SpotBookmarkRepository spotBookmarkRepository;

    public void addSpotBookmark(long spotId, User user) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        spotBookmarkRepository.findBySpotAndUser(spot, user).ifPresent(m -> {
            throw new ExistingSpotDittoException();
        });

        SpotBookmark spotBookmark = new SpotBookmark(spot, user);
        spotBookmarkRepository.save(spotBookmark);
    }

    public void removeSpotBookmark(long spotId, User user) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        SpotBookmark spotBookmark = spotBookmarkRepository.findBySpotAndUser(spot, user).orElseThrow(NoSuchElementException::new);
        spotBookmarkRepository.delete(spotBookmark);
    }

}
