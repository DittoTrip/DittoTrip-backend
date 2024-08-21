package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryData;

import java.util.ArrayList;
import java.util.List;

@Data
public class SpotListRes {

    private CategoryData categoryData;
    private List<SpotData> spotData = new ArrayList<>();
    private Integer count;

}
