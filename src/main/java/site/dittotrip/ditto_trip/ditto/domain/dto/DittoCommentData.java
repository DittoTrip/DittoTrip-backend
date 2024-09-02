package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DittoCommentData {

    private Long dittoCommentId;
    private String body;
    private LocalDateTime createdDateTime;

    private UserData userData;
    @Builder.Default
    private List<DittoCommentData> childCommentDataList = new ArrayList<>();

    private Boolean isMine;

    public static DittoCommentData parentFromEntity(DittoComment dittoComment, User reqUser) {
        DittoCommentData dittoCommentData = fromEntity(dittoComment, reqUser);
        dittoCommentData.putChildrenCommentData(dittoComment, reqUser);
        return dittoCommentData;
    }

    private void putChildrenCommentData(DittoComment dittoComment, User reqUser) {
        for (DittoComment childDittoComment : dittoComment.getChildDittoComments()) {
            this.childCommentDataList.add(fromEntity(childDittoComment, reqUser));
        }
    }

    private static DittoCommentData fromEntity(DittoComment dittoComment, User reqUser) {
        DittoCommentData dittoCommentData = DittoCommentData.builder()
                .dittoCommentId(dittoComment.getId())
                .body(dittoComment.getBody())
                .createdDateTime(dittoComment.getCreatedDateTime())
                .userData(UserData.fromEntity(dittoComment.getUser()))
                .build();

        dittoCommentData.putIsMine(reqUser);
        return dittoCommentData;
    }

    private void putIsMine(User reqUser) {
        if (reqUser == null) {
            this.isMine = Boolean.FALSE;
            return;
        }

        if (this.userData.getUserId() == reqUser.getId()) {
            this.isMine = Boolean.TRUE;
        } else {
            this.isMine = Boolean.FALSE;
        }
    }


}
