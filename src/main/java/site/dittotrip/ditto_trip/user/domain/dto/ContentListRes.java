package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.review.domain.Review;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContentListRes {

    private List<ContentData> contentDataList = new ArrayList<>();

    private Integer totalPages;

    public static ContentListRes fromReviews(Page<Review> page) {
        ContentListRes contentListRes = new ContentListRes();
        contentListRes.setTotalPages(page.getTotalPages());

        for (Review review : page.getContent()) {
            contentListRes.getContentDataList().add(ContentData.fromReviewEntity(review));
        }

        return contentListRes;
    }

    public static ContentListRes fromDittos(Page<Ditto> page) {
        ContentListRes contentListRes = new ContentListRes();
        contentListRes.setTotalPages(page.getTotalPages());

        for (Ditto ditto : page.getContent()) {
            contentListRes.getContentDataList().add(ContentData.fromDittoEntity(ditto));
        }

        return contentListRes;
    }

}
