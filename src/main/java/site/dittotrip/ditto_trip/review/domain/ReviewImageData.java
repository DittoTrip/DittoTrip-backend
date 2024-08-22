package site.dittotrip.ditto_trip.review.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewImageData {

    private Long id;
    private String imagePath;

    public static ReviewImageData fromEntity(ReviewImage reviewImage) {
        return ReviewImageData.builder()
                .id(reviewImage.getId())
                .imagePath(reviewImage.getImagePath())
                .build();
    }
}
