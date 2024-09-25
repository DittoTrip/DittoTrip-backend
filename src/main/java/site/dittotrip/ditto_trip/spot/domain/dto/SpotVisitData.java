package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpot;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewMiniData;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SpotVisitData {

    private Long spotVisitId;
    private Long spotId;
    private String spotName;
    private String address;
    private String imagePath;
    @Builder.Default
    private List<String> hashtags = new ArrayList<>();
    private LocalDateTime createdDateTime;

    private Long bookmarkId;
    private ReviewMiniData reviewMiniData;

    public static SpotVisitData fromEntity(SpotVisit spotVisit, Long bookmarkId) {
        Spot spot = spotVisit.getSpot();
        SpotVisitData spotVisitData = SpotVisitData.builder()
                .spotVisitId(spotVisit.getId())
                .spotId(spot.getId())
                .spotName(spot.getName())
                .address(spot.getAddress())
                .imagePath(spot.getImagePath())
                .createdDateTime(spotVisit.getCreatedDateTime())
                .bookmarkId(bookmarkId)
                .build();

        if (spotVisit.getReview() != null) {
            spotVisitData.setReviewMiniData(ReviewMiniData.fromEntity(spotVisit.getReview()));
        }

        for (HashtagSpot hashtagSpot : spot.getHashtagSpots()) {
            spotVisitData.getHashtags().add(hashtagSpot.getHashtag().getName());
        }

        return spotVisitData;
    }

}
