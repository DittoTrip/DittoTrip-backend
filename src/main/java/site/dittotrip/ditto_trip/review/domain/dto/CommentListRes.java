package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CommentListRes {

    private List<ReviewCommentData> reviewCommentDataList = new ArrayList<>();

}
