package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.Spot;

@Data
@Builder
public class SpotMiniData {

    private Long spotId;
    private String name;

    public static SpotMiniData fromEntity(Spot spot) {
        return SpotMiniData.builder()
                .spotId(spot.getId())
                .name(spot.getName())
                .build();
    }

}
