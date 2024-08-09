package site.dittotrip.ditto_trip.review.comment.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.review.comment.domain.Comment;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentData {

    private Long commentId;
    private UserData userData;
    private String body;
    private LocalDateTime createdDateTime;
    private List<CommentData> childrenCommentsData;

    public static CommentData parentFromEntity(Comment comment) {
        CommentData commentData = CommentData.builder()
                .commentId(comment.getId())
                .userData(UserData.fromEntity(comment.getUser()))
                .body(comment.getBody())
                .createdDateTime(comment.getCreatedDateTime())
                .build();

        commentData.putChildrenCommentData(comment);

        return commentData;
    }

    private static CommentData childFromEntity(Comment comment) {
        return CommentData.builder()
                .commentId(comment.getId())
                .userData(UserData.fromEntity(comment.getUser()))
                .body(comment.getBody())
                .createdDateTime(comment.getCreatedDateTime())
                .build();
    }

    public void putChildrenCommentData(Comment comment) {
        for (Comment childComment : comment.getChildComments()) {
            this.childrenCommentsData.add(childFromEntity(comment));
        }
    }

}