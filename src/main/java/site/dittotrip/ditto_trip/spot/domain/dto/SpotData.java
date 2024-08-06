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
    // 이미지 URI
    private float rating;
    private String address;
    private Point point;
    private List<Hashtag> hashtags = new ArrayList<>();

    /**
     * 미완성
     */
    public static SpotData fromEntity(Spot spot) {
        return SpotData.builder()
                .spotId(spot.getId())
                .build();
    }

}
