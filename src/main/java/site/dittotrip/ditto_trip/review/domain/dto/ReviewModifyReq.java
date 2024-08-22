package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.review.domain.Review;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ReviewModifyReq {

    private Float rating;
    private String reviewBody;
    private List<Long> removedImageIds = new ArrayList<>();

    public void modifyEntity(Review review) {
        review.setRating(this.getRating());
        review.setBody(this.getReviewBody());
    }

}
