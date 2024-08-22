package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewDetailRes {

    private String spotName;
    private ReviewData reviewData;
    private List<ReviewCommentData> reviewCommentDataList = new ArrayList<>();

}
