package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpotVisitListRes {

    private List<SpotVisitData> spotVisitDataList = new ArrayList<>();
    private Integer count;
    private Integer totalPages;

}
