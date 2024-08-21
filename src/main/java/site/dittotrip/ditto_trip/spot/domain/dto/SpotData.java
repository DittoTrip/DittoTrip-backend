package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.dto.HashtagData;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SpotData {

    private Long spotId;
    private String spotName;
    private String address;
    private Double pointX;
    private Double pointY;
    private String imagePath;
    private Float rating;

    private List<HashtagData> hashtagData = new ArrayList<>();
    private Boolean isBookmark;

    public static SpotData fromEntity(Spot spot, Boolean isBookmark) {
        SpotData spotData = SpotData.builder()
                .spotId(spot.getId())
                .spotName(spot.getSpotName())
                .address(spot.getAddress())
                .pointX(spot.getPointX())
                .pointY(spot.getPointY())
                .imagePath(spot.getImagePath())
                .rating(spot.getRating())
                .isBookmark(isBookmark)
                .build();

        spotData.putHashtagData(spot);

        return spotData;
    }

    private void putHashtagData(Spot spot) {
        for (Hashtag hashtag : spot.getHashtags()) {
            hashtagData.add(HashtagData.fromEntity(hashtag));
        }
    }

}
