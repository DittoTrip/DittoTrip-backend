package site.dittotrip.ditto_trip.mainpage.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.utils.Language;
import site.dittotrip.ditto_trip.utils.TranslationService;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SpotMainPageData {

    private Long spotId;
    private String name;
    private String categoryName;
    private String imagePath;

    public static List<SpotMainPageData> fromEntities(List<Spot> spots) {
        List<SpotMainPageData> dataList = new ArrayList<>();
        for (Spot spot : spots) {
            dataList.add(SpotMainPageData.fromEntity(spot));
        }
        return dataList;
    }

    private static SpotMainPageData fromEntity(Spot spot) {
        SpotMainPageData data = SpotMainPageData.builder()
                .spotId(spot.getId())
                .name(TranslationService.getLanguage() == Language.EN ? spot.getNameEN() : spot.getName())
                .imagePath(spot.getImagePath())
                .build();

        data.getCategoryName(spot);

        return data;
    }

    private void getCategoryName(Spot spot) {
        List<CategorySpot> categorySpots = spot.getCategorySpots();
        if (!categorySpots.isEmpty()) {
            this.categoryName = categorySpots.get(0).getCategory().getName();
        }
    }

}
