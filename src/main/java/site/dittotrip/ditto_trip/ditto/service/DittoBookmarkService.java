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

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DittoBookmarkService {

    private final DittoRepository dittoRepository;
    private final DittoBookmarkRepository dittoBookmarkRepository;

    public Boolean getDittoBookmark(Long dittoId, User user) {
        Ditto ditto = dittoRepository.findById(dittoId).orElseThrow(NoSuchElementException::new);
        Optional<DittoBookmark> optionalBookmark = dittoBookmarkRepository.findByDittoAndUser(ditto, user);
        return optionalBookmark.isPresent();
    }



    @Transactional(readOnly = false)
    public void saveDittoBookmark(Long dittoId, User user) {
        Ditto ditto = dittoRepository.findById(dittoId).orElseThrow(NoSuchElementException::new);

        dittoBookmarkRepository.findByDittoAndUser(ditto, user).ifPresent(m -> {
            throw new ExistingDittoBookmarkException();
        });

        DittoBookmark dittoBookmark = new DittoBookmark(ditto, user);
        dittoBookmarkRepository.save(dittoBookmark);
    }

    @Transactional(readOnly = false)
    public void removeDittoBookmark(Long dittoId, User user) {
        Ditto ditto = dittoRepository.findByIdWithUser(dittoId).orElseThrow(NoSuchElementException::new);
        DittoBookmark dittoBookmark = dittoBookmarkRepository.findByDittoAndUser(ditto, user).orElseThrow(NoSuchElementException::new);
        dittoBookmarkRepository.delete(dittoBookmark);
    }
}
