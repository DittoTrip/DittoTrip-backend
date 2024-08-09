package site.dittotrip.ditto_trip.review.comment.domain.dto.list;

import lombok.Data;
import site.dittotrip.ditto_trip.review.comment.domain.Comment;
import site.dittotrip.ditto_trip.review.comment.domain.dto.CommentData;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentListRes {

    private List<CommentData> commentDataList = new ArrayList<>();

    public static CommentListRes fromEntity(List<Comment> comments) {
        CommentListRes commentListRes = new CommentListRes();
        for (Comment comment : comments) {
            commentListRes.getCommentDataList().add(CommentData.parentFromEntity(comment));
        }

        return commentListRes;
    }

}
