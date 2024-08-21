package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotDetailRes;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;
import site.dittotrip.ditto_trip.spot.repository.SpotBookmarkRepository;
import site.dittotrip.ditto_trip.spot.domain.SpotImage;
import site.dittotrip.ditto_trip.spot.repository.StillCutRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final StillCutRepository stillCutRepository;
    private final ReviewRepository reviewRepository;
    private final SpotBookmarkRepository spotBookmarkRepository;

    public SpotDetailRes findSpotDetail(Long spotId, User user) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        List<SpotImage> SpotImages = stillCutRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        List<Review> reviews = reviewRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        SpotBookmark spotBookmark = spotBookmarkRepository.findBySpotAndUser(spot, user).orElse(null);

        return SpotDetailRes.fromEntity(spot, SpotImages, reviews, spotBookmark);
    }

}
