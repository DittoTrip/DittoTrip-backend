package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.image.domain.Image;
import site.dittotrip.ditto_trip.image.domain.dto.ImageData;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReviewData {

    private Float rating;
    private String reviewBody;
    private Integer likes;
    private LocalDateTime createdDateTime;
    private Integer commentsCount;

    private UserData userData;
    private List<ImageData> imageDataList;

    private Boolean myLike;

    public static ReviewData fromEntity(Review review, Boolean myLike) {
        ReviewData reviewData = ReviewData.builder()
                .rating(review.getRating())
                .reviewBody(review.getBody())
                .likes(review.getLikes())
                .createdDateTime(review.getCreatedDateTime())
                .userData(UserData.fromEntity(review.getUser()))
                .myLike(myLike)
                .build();

        reviewData.putImageDataList(review.getImages());

        return reviewData;
    }

    private void putImageDataList(List<Image> images) {
        for (Image image : images) {
            this.imageDataList.add(ImageData.fromEntity(image));
        }
    }

}