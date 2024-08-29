package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.review.domain.Review;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewSaveReq {

    private Float rating;
    private String reviewBody;

    public Review toEntity(User user, SpotVisit spotVisit) {
        return new Review(this.getReviewBody(),
                this.getRating(),
                LocalDateTime.now(),
                user,
                spotVisit);
    }

}
