package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.user.domain.User;

@Data
@NoArgsConstructor
public class ReviewSaveReq {

    private Float rating;
    private String body;

    public Review toEntity(User user, SpotVisit spotVisit) {
        return new Review(this.getBody(),
                this.getRating(),
                user,
                spotVisit);
    }

    public void modifyEntity(Review review) {
        review.setBody(this.getBody());
        review.setRating(this.rating);
    }

}
