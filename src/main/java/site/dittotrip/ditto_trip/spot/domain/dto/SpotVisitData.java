package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;

import java.time.LocalDateTime;

@Data
@Builder
public class SpotVisitData {

    private Long spotVisitId;
    private Long spotId;
    private String spotName;
    private String address;
    private LocalDateTime createdDateTime;

    private Boolean isBookmark;

    public static SpotVisitData fromEntity(SpotVisit spotVisit, Boolean isBookmark) {
        Spot spot = spotVisit.getSpot();
        return SpotVisitData.builder()
                .spotVisitId(spotVisit.getId())
                .spotId(spot.getId())
                .spotName(spot.getSpotName())
                .address(spot.getAddress())
                .createdDateTime(spotVisit.getCreatedDateTime())
                .isBookmark(isBookmark)
                .build();
    }

}
