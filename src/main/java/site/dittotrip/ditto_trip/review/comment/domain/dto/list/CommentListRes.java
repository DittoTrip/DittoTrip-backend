package site.dittotrip.ditto_trip.review.comment.domain.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.dittotrip.ditto_trip.review.comment.domain.Comment;
import site.dittotrip.ditto_trip.review.comment.domain.dto.CommentData;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class CommentListRes {

    private List<CommentData> commentDataList = new ArrayList<>();

}
