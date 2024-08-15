package site.dittotrip.ditto_trip.review.domain.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewData;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewListRes {

    private Integer reviewsCount;
    private Float rating;
    private List<ReviewData> reviewDataList = new ArrayList<>();



}
