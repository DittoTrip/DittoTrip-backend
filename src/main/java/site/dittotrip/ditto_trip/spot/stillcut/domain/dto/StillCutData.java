package site.dittotrip.ditto_trip.spot.stillcut.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.spot.stillcut.domain.StillCut;

@Data
@Builder
public class StillCutData {

    private Long stillCutId;
    private String body;
    private String imageFilePath;

    public static StillCutData fromEntity(StillCut stillCut) {
        return StillCutData.builder()
                .stillCutId(stillCut.getId())
                .body(stillCut.getBody())
                .imageFilePath(stillCut.getImage().getFilePath())
                .build();
    }

}
