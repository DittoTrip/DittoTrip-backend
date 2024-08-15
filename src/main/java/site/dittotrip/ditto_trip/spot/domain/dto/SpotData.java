package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.geo.Point;
import site.dittotrip.ditto_trip.hashtag.domain.entity.Hashtag;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SpotData {

    private Long spotId;
    private String spotName;
    private Float rating;
    private String address;
    private Point point;
    private String imageFilePath;
//    private List<Hashtag> hashtags = new ArrayList<>();

    /**
     * 미완성
     */
    public static SpotData fromEntity(Spot spot) {
        SpotData spotData = SpotData.builder()
                .spotId(spot.getId())
                .spotName(spot.getSpotName())
                .address(spot.getAddress())
                .point(spot.getPoint())
                .imageFilePath(spot.getImage().getFilePath())
                .build();

        return spotData;
    }

}
