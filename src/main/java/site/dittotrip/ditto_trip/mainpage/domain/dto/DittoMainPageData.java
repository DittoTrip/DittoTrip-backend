package site.dittotrip.ditto_trip.mainpage.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;

@Data
@Builder
public class DittoMainPageData {

    private Long dittoId;
    private String title;
    private String body;
    private String imagePath;

    public static DittoMainPageData fromEntity(Ditto ditto) {
        return DittoMainPageData.builder()
                .dittoId(ditto.getId())
                .title(ditto.getTitle())
                .body(ditto.getBody())
                .imagePath(ditto.getImagePath())
                .build();
    }

}
