package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentData {

    private Long commentId;
    private String body;
    private LocalDateTime createdDateTime;

    private UserData userData;
    private List<CommentData> childrenCommentsData;

    private Boolean isMine;

    public static CommentData parentFromEntity(ReviewComment reviewComment) {
        CommentData commentData = fromEntity(reviewComment);
        commentData.putChildrenCommentData(reviewComment);
        return commentData;
    }

    private static CommentData childFromEntity(ReviewComment reviewComment) {
        return fromEntity(reviewComment);
    }

    private static CommentData fromEntity(ReviewComment reviewComment) {
        return CommentData.builder()
                .commentId(reviewComment.getId())
                .userData(UserData.fromEntity(reviewComment.getUser()))
                .body(reviewComment.getBody())
                .createdDateTime(reviewComment.getCreatedDateTime())
                .build();
    }

    private void putChildrenCommentData(ReviewComment reviewComment) {
        for (ReviewComment childReviewComment : reviewComment.getChildReviewComments()) {
            this.childrenCommentsData.add(childFromEntity(childReviewComment));
        }
    }

}
