package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryData;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.spot.domain.*;
import site.dittotrip.ditto_trip.spot.domain.dto.*;
import site.dittotrip.ditto_trip.spot.repository.*;
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
    private final SpotVisitRepository spotVisitRepository;

    public SpotListInMapRes findSpotListInMap(Long categoryId, User user,
                                              Double startX, Double endX, Double startY, Double endY) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        List<CategorySpot> categorySpots = categorySpotRepository.findByCategoryInScope(category, startX, endX, startY, endY);

        SpotListInMapRes spotListInMapRes = new SpotListInMapRes();
        spotListInMapRes.setCategoryData(CategoryData.fromEntity(category));
        for (CategorySpot categorySpot : categorySpots) {
            Spot spot = categorySpot.getSpot();
            Long bookmarkId = getBookmarkId(spot, user);
            spotListInMapRes.getSpotDataList().add(SpotData.fromEntity(spot, bookmarkId));
        }
        spotListInMapRes.setSpotCount(categorySpots.size());

        return spotListInMapRes;
    }

    public SpotListRes findSpotListByBookmark(User user) {
        List<SpotBookmark> spotBookmarks = spotBookmarkRepository.findByUser(user);
        return SpotListRes.fromEntitiesByBookmark(spotBookmarks);
    }


    public SpotListRes findSpotListBySearch(User user, String word, Pageable pageable) {
        List<Spot> spots = spotRepository.findBySpotNameContaining(word, pageable);

        SpotListRes spotListRes = new SpotListRes();
        spotListRes.setSpotCount(spots.size());
        for (Spot spot : spots) {
            Long bookmarkId = getBookmarkId(spot, user);
            spotListRes.getSpotDataList().add(SpotData.fromEntity(spot, bookmarkId));
        }

        return spotListRes;
    }

    public SpotVisitListRes findSpotVisitList(User user, Pageable pageable) {
        Page<SpotVisit> page = spotVisitRepository.findByUser(user, pageable);

        List<SpotVisit> spotVisits = page.getContent();

        SpotVisitListRes spotVisitListRes = new SpotVisitListRes();
        spotVisitListRes.setCount((int) page.getTotalElements());
        spotVisitListRes.setTotalPages(page.getTotalPages());

        for (SpotVisit spotVisit : spotVisits) {
            Long bookmarkId = getBookmarkId(spotVisit.getSpot(), user);
            spotVisitListRes.getSpotVisitDataList().add(SpotVisitData.fromEntity(spotVisit, bookmarkId));
        }

        return spotVisitListRes;
    }

    public SpotDetailRes findSpotDetail(Long spotId, User user) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        List<SpotImage> SpotImages = stillCutRepository.findTop3BySpotOrderByCreatedDateTimeDesc(spot);
        List<Review> reviews = reviewRepository.findTop3BySpot(spot);
        Long bookmarkId = getBookmarkId(spot, user);

        return SpotDetailRes.fromEntity(spot, SpotImages, reviews, bookmarkId);
    }

    private Long getBookmarkId(Spot spot, User user) {
        if (user == null) {
            return null;
        }
        Optional<SpotBookmark> findBookmark = spotBookmarkRepository.findBySpotAndUser(spot, user);
        return findBookmark.map(SpotBookmark::getId).orElse(null);
    }

}
