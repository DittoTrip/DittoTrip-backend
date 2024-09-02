package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.dto.HashtagData;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SpotData {

    private Long spotId;
    private String name;
    private String address;
    private Double pointX;
    private Double pointY;
    private String imagePath;
    private Float rating;

    @Builder.Default
    private List<HashtagData> hashtagData = new ArrayList<>();
    private Long myBookmarkId;

    public static SpotData fromEntity(Spot spot, Long bookmarkId) {
        SpotData spotData = SpotData.builder()
                .spotId(spot.getId())
                .name(spot.getName())
                .address(spot.getAddress())
                .pointX(spot.getPointX())
                .pointY(spot.getPointY())
                .imagePath(spot.getImagePath())
                .rating(spot.getRating())
                .myBookmarkId(bookmarkId)
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
