package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewMiniData;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotBookmark;
import site.dittotrip.ditto_trip.spot.domain.SpotImage;

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
    private List<SpotImageData> spotImageDataList = new ArrayList<>();
    private List<ReviewMiniData> reviewDataList = new ArrayList<>();
    private Boolean spotDittoData;
    // 사진 가이드
    // 주변 관광지

    public static SpotDetailRes fromEntity(Spot spot, List<SpotImage> SpotImages, List<Review> reviews, SpotBookmark spotBookmark) {
        SpotDetailRes spotDetailRes = new SpotDetailRes();

        spotDetailRes.setSpotData(SpotData.fromEntity(spot));
        spotDetailRes.putStillCutDataList(SpotImages);
        spotDetailRes.putReviewDataList(reviews);
        spotDetailRes.putSpotDittoData(spotBookmark);

        return spotDetailRes;
    }

    private void putStillCutDataList(List<SpotImage> SpotImages) {
        List<SpotImageData> spotImageDataList = new ArrayList<>();
        for (SpotImage spotImage : SpotImages) {
            spotImageDataList.add(SpotImageData.fromEntity(spotImage));
        }
        this.setSpotImageDataList(spotImageDataList);
    }

    private void putReviewDataList(List<Review> reviews) {
        List<ReviewMiniData> reviewDataList = new ArrayList<>();
        for (Review review : reviews) {
            reviewDataList.add(ReviewMiniData.fromEntity(review));
        }
        this.setReviewDataList(reviewDataList);
    }

    private void putSpotDittoData(SpotBookmark spotBookmark) {
        if (spotBookmark != null) {
            this.spotDittoData = Boolean.FALSE;
        } else {
            this.spotDittoData = Boolean.TRUE;
        }
    }

}
