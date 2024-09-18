package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;

@Data
public class SpotApplyDetailRes {

    private SpotApplyData spotApplyData;

    public static SpotApplyDetailRes fromEntity(SpotApply spotApply) {
        return new SpotApplyDetailRes(SpotApplyData.fromEntity(spotApply));
    }

    public SpotApplyDetailRes(SpotApplyData spotApplyData) {
        this.spotApplyData = spotApplyData;
    }
}
