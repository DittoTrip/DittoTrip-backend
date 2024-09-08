package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpotApplyListRes {

    private List<SpotApplyMiniData> spotApplyMiniDataList = new ArrayList<>();
    private Integer totalPages;

    public static SpotApplyListRes fromEntities(List<SpotApply> spotApplies) {
        SpotApplyListRes spotApplyListRes = new SpotApplyListRes();
        for (SpotApply spotApply : spotApplies) {
            spotApplyListRes.getSpotApplyMiniDataList().add(SpotApplyMiniData.fromEntity(spotApply));
        }
        return spotApplyListRes;
    }

}
