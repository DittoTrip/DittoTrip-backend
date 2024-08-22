package site.dittotrip.ditto_trip.review.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.review.domain.ReviewComment;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReviewCommentData {

    private Long commentId;
    private String body;
    private LocalDateTime createdDateTime;

    private UserData userData;
    private List<ReviewCommentData> childrenCommentsData;

    private Boolean isMine = Boolean.FALSE;

    public static ReviewCommentData parentFromEntity(ReviewComment reviewComment, User requestUser) {
        ReviewCommentData reviewCommentData = fromEntity(reviewComment, requestUser);
        reviewCommentData.putChildrenCommentData(reviewComment, requestUser);
        return reviewCommentData;
    }

    private static ReviewCommentData childFromEntity(ReviewComment reviewComment, User requestUser) {
        return fromEntity(reviewComment, requestUser);
    }

    private static ReviewCommentData fromEntity(ReviewComment reviewComment, User requestUser) {
        ReviewCommentData reviewCommentData = ReviewCommentData.builder()
                .commentId(reviewComment.getId())
                .body(reviewComment.getBody())
                .createdDateTime(reviewComment.getCreatedDateTime())
                .userData(UserData.fromEntity(reviewComment.getUser()))
                .build();

        reviewCommentData.putIsMine(requestUser);
        return reviewCommentData;
    }

    private void putChildrenCommentData(ReviewComment reviewComment, User requestUser) {
        for (ReviewComment childReviewComment : reviewComment.getChildReviewComments()) {
            this.childrenCommentsData.add(childFromEntity(childReviewComment, requestUser));
        }
    }

    private void putIsMine(User requestUser) {
        if (this.userData.getUserId().equals(requestUser.getId())) {
            this.isMine = Boolean.TRUE;
        }
    }

}
