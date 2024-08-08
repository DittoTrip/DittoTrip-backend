package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.dto.detail.SpotDetailRes;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.spot.stillcut.domain.StillCut;
import site.dittotrip.ditto_trip.spot.stillcut.repository.StillCutRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotDetailService {

    private final SpotRepository spotRepository;
    private final StillCutRepository stillCutRepository;
    private final ReviewRepository reviewRepository;

    public SpotDetailRes findSpotDetail(Long spotId) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        List<StillCut> stillCuts = stillCutRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        List<Review> reviews = reviewRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);

        return SpotDetailRes.fromEntity(spot, stillCuts, reviews);
    }

}
