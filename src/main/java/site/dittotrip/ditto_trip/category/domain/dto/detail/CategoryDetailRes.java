package site.dittotrip.ditto_trip.category.domain.dto.detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryData;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotData;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDetailRes {

    private CategoryData categoryData;
    private List<SpotData> spotData;

    public static CategoryDetailRes fromEntity(Category category, List<Spot> spots) {

        CategoryData categoryData = CategoryData.fromEntity(category);
        List<SpotData> spotDataList = new ArrayList<>();
        for (Spot spot : spots) {
            spotDataList.add(SpotData.fromEntity(spot));
        }

        return new CategoryDetailRes(categoryData, spotDataList);
    }

}
