package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
import site.dittotrip.ditto_trip.spot.domain.dto.SpotListInMapRes;
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

    private static final int PAGE_SIZE = 10;

    public SpotListInMapRes findSpotListInMap(Long categoryId, User user,
                                              Double startX, Double endX, Double startY, Double endY) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        List<CategorySpot> categorySpots = categorySpotRepository.findByCategoryInScope(category, startX, endX, startY, endY);

        SpotListInMapRes spotListInMapRes = new SpotListInMapRes();
        spotListInMapRes.setCategoryData(CategoryData.fromEntity(category));
        for (CategorySpot categorySpot : categorySpots) {
            Spot spot = categorySpot.getSpot();
            Optional<SpotBookmark> spotBookmarkOptional = spotBookmarkRepository.findBySpotAndUser(spot, user);
            spotListInMapRes.getSpotDataList().add(SpotData.fromEntity(spot, spotBookmarkOptional.isPresent()));
        }
        spotListInMapRes.setCount(categorySpots.size());

        return spotListInMapRes;
    }

    public SpotListRes findSpotListByBookmark(User user) {
        List<SpotBookmark> spotBookmarks = spotBookmarkRepository.findByUser(user);
        return SpotListRes.fromEntitiesByBookmark(spotBookmarks);
    }


    public SpotListRes findSpotListBySearch(User user, String word, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        List<Spot> spots = spotRepository.findBySpotNameContaining(word, pageRequest);

        SpotListRes spotListRes = new SpotListRes();
        for (Spot spot : spots) {
            Optional<SpotBookmark> spotBookmarkOptional = spotBookmarkRepository.findBySpotAndUser(spot, user);
            spotListRes.getSpotDataList().add(SpotData.fromEntity(spot, spotBookmarkOptional.isPresent()));
        }

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
