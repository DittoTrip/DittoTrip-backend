package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewData;
import site.dittotrip.ditto_trip.spot.domain.Spot;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ReviewListRes {

    private Integer reviewsCount;
    private Float rating;
    @Builder.Default
    private List<ReviewData> reviewDataList = new ArrayList<>();
    private Integer totalPage;

}
