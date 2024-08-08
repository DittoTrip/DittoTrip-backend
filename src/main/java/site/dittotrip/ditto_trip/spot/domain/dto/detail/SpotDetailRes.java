package site.dittotrip.ditto_trip.spot.domain.dto.detail;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryData;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.dto.mini.ReviewMiniData;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotData;
import site.dittotrip.ditto_trip.spot.spotditto.domain.SpotDitto;
import site.dittotrip.ditto_trip.spot.stillcut.domain.StillCut;
import site.dittotrip.ditto_trip.spot.stillcut.domain.dto.StillCutData;

import java.util.ArrayList;
import java.util.List;

/**
 * 미완성
 *  - 사진 가이드, 주변 관광지 기획 및 디자인 확정 후 수정
 */
@Data
public class SpotDetailRes {

    private SpotData spotData;
//    private CategoryData categoryData;
    private List<StillCutData> stillCutDataList = new ArrayList<>();
    private List<ReviewMiniData> reviewDataList = new ArrayList<>();
    private Boolean spotDittoData;
    // 사진 가이드
    // 주변 관광지

    public static SpotDetailRes fromEntity(Spot spot, List<StillCut> stillCuts, List<Review> reviews, SpotDitto spotDitto) {
        SpotDetailRes spotDetailRes = new SpotDetailRes();

        spotDetailRes.setSpotData(SpotData.fromEntity(spot));
        spotDetailRes.putStillCutDataList(stillCuts);
        spotDetailRes.putReviewDataList(reviews);
        spotDetailRes.putSpotDittoData(spotDitto);

        return spotDetailRes;
    }

    private void putStillCutDataList(List<StillCut> stillCuts) {
        List<StillCutData> stillCutDataList = new ArrayList<>();
        for (StillCut stillCut : stillCuts) {
            stillCutDataList.add(StillCutData.fromEntity(stillCut));
        }
        this.setStillCutDataList(stillCutDataList);
    }

    private void putReviewDataList(List<Review> reviews) {
        List<ReviewMiniData> reviewDataList = new ArrayList<>();
        for (Review review : reviews) {
            reviewDataList.add(ReviewMiniData.fromEntity(review));
        }
        this.setReviewDataList(reviewDataList);
    }

    private void putSpotDittoData(SpotDitto spotDitto) {
        if (spotDitto != null) {
            this.spotDittoData = Boolean.FALSE;
        } else {
            this.spotDittoData = Boolean.TRUE;
        }
    }

}
