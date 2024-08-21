package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryData;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotData;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotDetailRes;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotListRes;
import site.dittotrip.ditto_trip.spot.repository.CategorySpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;
import site.dittotrip.ditto_trip.spot.repository.SpotBookmarkRepository;
import site.dittotrip.ditto_trip.spot.domain.SpotImage;
import site.dittotrip.ditto_trip.spot.repository.StillCutRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotService {

    private final SpotRepository spotRepository;
    private final CategoryRepository categoryRepository;
    private final CategorySpotRepository categorySpotRepository;
    private final StillCutRepository stillCutRepository;
    private final ReviewRepository reviewRepository;
    private final SpotBookmarkRepository spotBookmarkRepository;

    public SpotListRes findSpotList(Long categoryId, User user,
                                    Double startX, Double endX, Double startY, Double endY) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        List<CategorySpot> categorySpots = categorySpotRepository.findByCategoryInScope(category, startX, endX, startY, endY);

        SpotListRes spotListRes = new SpotListRes();
        spotListRes.setCategoryData(CategoryData.fromEntity(category));
        for (CategorySpot categorySpot : categorySpots) {
            Spot spot = categorySpot.getSpot();
            Optional<SpotBookmark> spotBookmarkOptional = spotBookmarkRepository.findBySpotAndUser(spot, user);
            spotListRes.getSpotData().add(SpotData.fromEntity(spot, spotBookmarkOptional.isPresent()));
        }
        spotListRes.setCount(categorySpots.size());

        return spotListRes;
    }

    public SpotDetailRes findSpotDetail(Long spotId, User user) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        List<SpotImage> SpotImages = stillCutRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        List<Review> reviews = reviewRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        Optional<SpotBookmark> spotBookmarkOptional = spotBookmarkRepository.findBySpotAndUser(spot, user);

        return SpotDetailRes.fromEntity(spot, SpotImages, reviews, spotBookmarkOptional.isPresent());
    }

}
