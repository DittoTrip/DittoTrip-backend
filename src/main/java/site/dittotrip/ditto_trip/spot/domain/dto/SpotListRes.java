package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.review.utils.DistanceCalculator;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpotListRes {

    private List<SpotData> spotDataList = new ArrayList<>();
    private Integer spotCount;

    public static SpotListRes fromEntitiesByBookmark(List<SpotBookmark> spotBookmarks,
                                                     Double userX, Double userY) {
        SpotListRes spotListRes = new SpotListRes();
        spotListRes.setSpotCount(spotBookmarks.size());

        for (SpotBookmark spotBookmark : spotBookmarks) {
            Spot spot = spotBookmark.getSpot();
            Double distance = DistanceCalculator.getDistanceTwoPoints(userX, userY, spot.getPointX(), spot.getPointY());
            spotListRes.getSpotDataList().add(SpotData.fromEntity(spot, spotBookmark.getId(), distance));
        }

        return spotListRes;
    }

}
