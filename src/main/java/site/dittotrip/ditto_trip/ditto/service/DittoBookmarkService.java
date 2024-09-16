package site.dittotrip.ditto_trip.ditto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoBookmark;
import site.dittotrip.ditto_trip.ditto.exception.ExistingDittoBookmarkException;
import site.dittotrip.ditto_trip.ditto.repository.DittoBookmarkRepository;
import site.dittotrip.ditto_trip.ditto.repository.DittoRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DittoBookmarkService {

    private final UserRepository userRepository;
    private final DittoRepository dittoRepository;
    private final DittoBookmarkRepository dittoBookmarkRepository;

    public Boolean getDittoBookmark(Long reqUserId, Long dittoId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Ditto ditto = dittoRepository.findById(dittoId).orElseThrow(NoSuchElementException::new);
        Optional<DittoBookmark> optionalBookmark = dittoBookmarkRepository.findByDittoAndUser(ditto, reqUser);
        return optionalBookmark.isPresent();
    }



    @Transactional(readOnly = false)
    public void saveDittoBookmark(Long reqUserId, Long dittoId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Ditto ditto = dittoRepository.findById(dittoId).orElseThrow(NoSuchElementException::new);

        dittoBookmarkRepository.findByDittoAndUser(ditto, reqUser).ifPresent(m -> {
            throw new ExistingDittoBookmarkException();
        });

        DittoBookmark dittoBookmark = new DittoBookmark(ditto, reqUser);
        dittoBookmarkRepository.save(dittoBookmark);
    }

    @Transactional(readOnly = false)
    public void removeDittoBookmark(Long reqUserId, Long dittoId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Ditto ditto = dittoRepository.findByIdWithUser(dittoId).orElseThrow(NoSuchElementException::new);
        DittoBookmark dittoBookmark = dittoBookmarkRepository.findByDittoAndUser(ditto, reqUser).orElseThrow(NoSuchElementException::new);
        dittoBookmarkRepository.delete(dittoBookmark);
    }
}
