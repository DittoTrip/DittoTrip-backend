package site.dittotrip.ditto_trip.image.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.image.domain.Image;

@Data
@Builder
public class ImageData {

    private String filePath;

    public static ImageData fromEntity(Image image) {
        return ImageData.builder()
                .filePath(image.getFilePath()).build();
    }

}
