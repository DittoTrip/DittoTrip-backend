package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpotListRes {

    private List<SpotData> spotDataList = new ArrayList<>();
    private Integer spotCount;

    public static SpotListRes fromEntitiesByBookmark(List<SpotBookmark> spotBookmarks) {
        SpotListRes spotListRes = new SpotListRes();
        spotListRes.setSpotCount(spotBookmarks.size());

        for (SpotBookmark spotBookmark : spotBookmarks) {
            spotListRes.getSpotDataList().add(SpotData.fromEntity(spotBookmark.getSpot(), spotBookmark.getId()));
        }

        return spotListRes;
    }

}
