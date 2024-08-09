package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.dto.save.ReviewSaveReq;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ReviewSaveService {

    private SpotRepository spotRepository;
    private ReviewRepository reviewRepository;

    public void saveReview(Long spotId, User user, ReviewSaveReq reviewSaveReq, List<MultipartFile> multipartFiles) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        Review review = new Review(reviewSaveReq.getReviewBody(),
                reviewSaveReq.getRating(),
                LocalDateTime.now(),
                user,
                spot,
                null);

        reviewRepository.save(review);
    }

}
