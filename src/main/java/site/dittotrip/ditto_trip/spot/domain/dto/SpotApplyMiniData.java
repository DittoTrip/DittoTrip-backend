package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;
import site.dittotrip.ditto_trip.spot.domain.enums.SpotApplyStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class SpotApplyMiniData {

    private Long id;
    private String name;
    private SpotApplyStatus spotApplyStatus;
    private LocalDateTime createdDateTime;

    public static SpotApplyMiniData fromEntity(SpotApply spotApply) {
        return SpotApplyMiniData.builder()
                .id(spotApply.getId())
                .name(spotApply.getName())
                .spotApplyStatus(spotApply.getSpotApplyStatus())
                .createdDateTime(spotApply.getCreatedDateTime())
                .build();
    }

}
