package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpotApplyMiniListRes {

    private List<SpotApplyMiniData> spotApplyMiniDataList = new ArrayList<>();
    private Integer totalPages;

    public static SpotApplyMiniListRes fromEntities(List<SpotApply> spotApplies) {
        SpotApplyMiniListRes spotApplyMiniListRes = new SpotApplyMiniListRes();
        for (SpotApply spotApply : spotApplies) {
            spotApplyMiniListRes.getSpotApplyMiniDataList().add(SpotApplyMiniData.fromEntity(spotApply));
        }
        return spotApplyMiniListRes;
    }

}
