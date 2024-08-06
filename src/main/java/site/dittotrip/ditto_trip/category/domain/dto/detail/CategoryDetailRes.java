package site.dittotrip.ditto_trip.category.domain.dto.detail;

import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryData;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotData;

import java.util.List;

@Data
public class CategoryDetailRes {

    private CategoryData categoryData;
    private List<SpotData> spotData;

}
