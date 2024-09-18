package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.review.domain.Review;

@Data
@Builder
public class ContentData {
    
    private Long id;
    private String title;
    private String body;

    public static ContentData fromReviewEntity(Review review) {
        return ContentData.builder()
                .id(review.getId())
                .title(review.getSpotVisit().getSpot().getName())
                .body(review.getBody())
                .build();
    }

    public static ContentData fromDittoEntity(Ditto ditto) {
        return ContentData.builder()
                .id(ditto.getId())
                .title(ditto.getTitle())
                .body(ditto.getBody())
                .build();
    }


}
