package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpotApplyListRes {

    private List<SpotApplyData> spotApplyDataList = new ArrayList<>();

    private Integer totalPages;

    public static SpotApplyListRes fromEntities(Page<SpotApply> page) {
        SpotApplyListRes res = new SpotApplyListRes();
        res.setTotalPages(page.getTotalPages());

        for (SpotApply spotApply : page.getContent()) {
            res.getSpotApplyDataList().add(SpotApplyData.fromEntity(spotApply));
        }

        return res;
    }

}
