package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.SpotImage;

@Data
@Builder
public class SpotImageData {

    private Long spotImageId;
    private String imagePath;

    public static SpotImageData fromEntity(SpotImage spotImage) {
        return SpotImageData.builder()
                .spotImageId(spotImage.getId())
                .imagePath(spotImage.getImagePath())
                .build();
    }

}
