package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.review.domain.Review;

/**
 * 미완성
 *  - user 엔티티 받고 UserData 담기 (Id, name)
 */
@Data
@Builder
public class ReviewMiniData {

    private Long reviewId;
    private String username;
    private float rating;
    private String reviewBody;

    public static ReviewMiniData fromEntity(Review review) {
        return ReviewMiniData.builder()
                .reviewId(review.getId())
                .username(review.getUser().getName())
                .rating(review.getRating())
                .reviewBody(review.getBody())
                .build();
    }

}
