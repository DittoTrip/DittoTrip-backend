package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewSaveReq {

    private Float rating;
    private String reviewBody;

}
