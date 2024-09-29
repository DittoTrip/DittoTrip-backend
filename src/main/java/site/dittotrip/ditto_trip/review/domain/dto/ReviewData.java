package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.ReviewImage;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;
import site.dittotrip.ditto_trip.utils.Language;
import site.dittotrip.ditto_trip.utils.TranslationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ReviewData {

    private Long reviewId;
    private Float rating;
    private String reviewBody;
    private Integer likes;
    private LocalDateTime createdDateTime;

    private UserData userData;
    @Builder.Default
    private List<String> imagePaths = new ArrayList<>();

    private Boolean isMine;
    private Boolean myLike;
    private Integer commentsCount;

    public static ReviewData fromEntity(Review review, Boolean isMine, Boolean myLike, Integer commentsCount) {
        ReviewData reviewData = ReviewData.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
            .reviewBody(TranslationService.getLanguage() == Language.EN ? review.getBodyEN() : review.getBody())
//                .reviewBody(review.getBody())
                .likes(review.getLikes())
                .createdDateTime(review.getCreatedDateTime())
                .userData(UserData.fromEntity(review.getUser()))
                .isMine(isMine)
                .myLike(myLike)
                .commentsCount(commentsCount)
                .build();

        reviewData.putImageDataList(review.getReviewImages());

        return reviewData;
    }

    private void putImageDataList(List<ReviewImage> reviewImages) {
        for (ReviewImage reviewImage : reviewImages) {
            this.imagePaths.add(reviewImage.getImagePath());
        }
    }

}
