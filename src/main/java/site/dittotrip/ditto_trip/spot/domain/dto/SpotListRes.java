package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.review.utils.DistanceCalculator;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpotListRes {

    private List<SpotData> spotDataList = new ArrayList<>();

    private Integer totalPages;

    public static SpotListRes fromEntitiesByBookmark(List<SpotBookmark> spotBookmarks,
                                                     Double userX, Double userY) {
        SpotListRes spotListRes = new SpotListRes();

        for (SpotBookmark spotBookmark : spotBookmarks) {
            Spot spot = spotBookmark.getSpot();
            Double distance = DistanceCalculator.getDistanceTwoPoints(userX, userY, spot.getPointX(), spot.getPointY());
            spotListRes.getSpotDataList().add(SpotData.fromEntity(spot, spotBookmark.getId(), distance));
        }

        return spotListRes;
    }

    public static SpotListRes fromEntitiesForNoAuth(Page<Spot> page,
                                                   Double userX, Double userY) {
        SpotListRes res = new SpotListRes();
        res.setTotalPages(page.getTotalPages());

        for (Spot spot : page.getContent()) {
            Double distance = DistanceCalculator.getDistanceTwoPoints(userX, userY, spot.getPointX(), spot.getPointY());
            res.getSpotDataList().add(SpotData.fromEntityForNoAuth(spot, distance));
        }

        return res;
    }

}
