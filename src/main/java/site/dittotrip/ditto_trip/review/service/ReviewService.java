package site.dittotrip.ditto_trip.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.review.repository.CommentRepository;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewData;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewListRes;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewModifyReq;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewSaveReq;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.review.exception.TooManyImagesException;
import site.dittotrip.ditto_trip.review.repository.ReviewRepository;
import site.dittotrip.ditto_trip.review.domain.ReviewLike;
import site.dittotrip.ditto_trip.review.repository.ReviewLikeRepository;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final SpotRepository spotRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final ReviewLikeRepository reviewLikeRepository;

    public ReviewListRes findReviewList(Long spotId, User user, PageRequest pageRequest) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);
        List<Review> reviews = reviewRepository.findBySpot(spot, pageRequest);

        Integer reviewCount = reviewRepository.countBySpot(spot).intValue();

        Float totalRating = 0.0f;

        List<ReviewData> reviewDataList = new ArrayList<>();
        for (Review review : reviews) {
            Long commentsCount = commentRepository.countByReview(review);
            Boolean isMine = getIsMine(user);
            Boolean myLike = getMyLike(review, user);

            reviewDataList.add(ReviewData.fromEntity(review, isMine, myLike, commentsCount.intValue()));
            totalRating += review.getRating();
        }

        Float avgRating = calculateAvgOfRating(totalRating, reviewCount);

        return new ReviewListRes(reviewCount, avgRating, reviewDataList);
    }

    @Transactional(readOnly = false)
    public void saveReview(Long spotId, User user, ReviewSaveReq reviewSaveReq, List<MultipartFile> multipartFiles) {
        Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

        if (multipartFiles.size() > 10) {
            throw new TooManyImagesException();
        }

        Review review = new Review(reviewSaveReq.getReviewBody(),
                reviewSaveReq.getRating(),
                LocalDateTime.now(),
                user,
                spot);

        // image 처리

        reviewRepository.save(review);
    }

    @Transactional(readOnly = false)
    public void modifyReview(Long reviewId, User user, ReviewModifyReq modifyReq, List<MultipartFile> multipartFiles) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);

        if (!review.getUser().equals(user)) {
             throw new NoAuthorityException();
        }

        if (review.getReviewImages().size() + multipartFiles.size() - modifyReq.getRemovedImageIds().size() > 10) {
            throw new TooManyImagesException();
        }

        modifyReq.modifyEntity(review);

        // 이미지 처리

        modifyReq.modifyEntity(review);
    }

    @Transactional(readOnly = false)
    public void removeReview(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(NoSuchElementException::new);

        if (!review.getUser().equals(user)) {
             throw new NoAuthorityException();
        }

        reviewRepository.delete(review);
    }

    /**
     * @return 0.0f, 0.5f, 1.0f, 1.5f, 2.0f, ... 4.5f, 5.0f
     */
    private Float calculateAvgOfRating(Float totalRating, Integer reviewCount) {
        float avg = totalRating / reviewCount.floatValue();
        return Math.round(avg * 20) / 20.0f;
    }

    private Boolean getIsMine(User user) {
        if (user == null) {
            return Boolean.FALSE;
        }

        Optional<Review> findReview = reviewRepository.findByUser(user);
        if (findReview.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private Boolean getMyLike(Review review, User user) {
        if (user == null) {
            return Boolean.FALSE;
        }

        Optional<ReviewLike> findReviewLike = reviewLikeRepository.findByReviewAndUser(review, user);
        if (findReviewLike.isPresent()) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

}
