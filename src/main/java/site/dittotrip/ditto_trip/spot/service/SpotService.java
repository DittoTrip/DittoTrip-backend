package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.dto.detail.SpotDetailRes;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.spot.spotditto.domain.SpotDitto;
import site.dittotrip.ditto_trip.spot.spotditto.repository.SpotDittoRepository;
import site.dittotrip.ditto_trip.spot.stillcut.domain.StillCut;
import site.dittotrip.ditto_trip.spot.stillcut.repository.StillCutRepository;
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
    private final SpotDittoRepository spotDittoRepository;

    public SpotDetailRes findSpotDetail(Long spotId, User user) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        List<StillCut> stillCuts = stillCutRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        List<Review> reviews = reviewRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        SpotDitto spotDitto = spotDittoRepository.findBySpotAndUser(spot, user).orElse(null);

        return SpotDetailRes.fromEntity(spot, stillCuts, reviews, spotDitto);
    }

}
