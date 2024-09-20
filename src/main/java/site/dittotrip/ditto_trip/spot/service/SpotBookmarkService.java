package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.exception.common.NoAuthorityException;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;
import site.dittotrip.ditto_trip.spot.exception.ExistingSpotBookmarkException;
import site.dittotrip.ditto_trip.spot.repository.SpotBookmarkRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotBookmarkService {

    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final SpotBookmarkRepository spotBookmarkRepository;

    public Boolean findSpotBookmark(Long reqUserId, Long spotId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);
        Optional<SpotBookmark> optionalBookmark = spotBookmarkRepository.findBySpotAndUser(spot, reqUser);
        return optionalBookmark.isPresent();
    }

    @Transactional(readOnly = false)
    public void addSpotBookmark(Long reqUserId, Long spotId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        spotBookmarkRepository.findBySpotAndUser(spot, reqUser).ifPresent(m -> {
            throw new ExistingSpotBookmarkException();
        });

        SpotBookmark spotBookmark = new SpotBookmark(spot, reqUser);
        spotBookmarkRepository.save(spotBookmark);
    }

    @Transactional(readOnly = false)
    public void removeSpotBookmark(Long reqUserId, Long bookmarkId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        SpotBookmark bookmark = spotBookmarkRepository.findById(bookmarkId).orElseThrow(NoSuchElementException::new);

        if (bookmark.getUser().getId() != reqUser.getId()) {
            throw new NoAuthorityException();
        }

        spotBookmarkRepository.delete(bookmark);
    }

}
