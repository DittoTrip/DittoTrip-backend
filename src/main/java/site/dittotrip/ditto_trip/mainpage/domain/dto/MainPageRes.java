package site.dittotrip.ditto_trip.mainpage.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.List;

@Data
public class MainPageRes {

    // Ditto 랜덤 1개
    private DittoMainPageData dittoData;

    // Category 랜덤 5개 (같은 CategorySubType)
    private CategorySubType categorySubType;
    private List<CategoryMainPageData> categoryDataList;

    // Spot 랜덤 6개
    private List<SpotMainPageData> spotDataList;

    private Boolean isNotCheckedAlarm;

    public static MainPageRes fromEntities(Ditto ditto,
                                           List<Category> categories,
                                           List<Spot> spots,
                                           Boolean isNotCheckedAlarm) {
        MainPageRes mainPageRes = new MainPageRes();
        mainPageRes.setDittoData(DittoMainPageData.fromEntity(ditto));
        mainPageRes.setCategoryDataList(CategoryMainPageData.fromEntities(categories));
        mainPageRes.setSpotDataList(SpotMainPageData.fromEntities(spots));
        mainPageRes.setIsNotCheckedAlarm(isNotCheckedAlarm);

        if (!categories.isEmpty()) {
            mainPageRes.setCategorySubType(categories.get(0).getCategorySubType());
        }

        return mainPageRes;
    }

}
