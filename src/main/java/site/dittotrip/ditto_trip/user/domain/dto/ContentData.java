package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.utils.Language;
import site.dittotrip.ditto_trip.utils.TranslationService;

@Data
@Builder
public class ContentData {
    
    private Long id;
    private String title;
    private String body;

    public static ContentData fromReviewEntity(Review review) {
        return ContentData.builder()
                .id(review.getId())
                .title(TranslationService.getLanguage() == Language.EN ? review.getSpotVisit().getSpot().getNameEN() : review.getSpotVisit().getSpot().getName())
                .body(TranslationService.getLanguage() == Language.EN ? review.getBodyEN() : review.getBody())
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
