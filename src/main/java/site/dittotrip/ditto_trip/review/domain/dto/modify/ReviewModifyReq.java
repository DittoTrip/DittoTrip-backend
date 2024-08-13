package site.dittotrip.ditto_trip.review.domain.dto.modify;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.review.domain.Review;

@Data
@NoArgsConstructor
public class ReviewModifyReq {

    private Float rating;
    private String reviewBody;
    // Removed Images
    // Added Images

    public void modifyEntity(Review review) {
        review.setRating(this.getRating());
        review.setBody(this.getReviewBody());
    }

}
