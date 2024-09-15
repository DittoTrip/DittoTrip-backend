package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.keyvalue.repository.query.PredicateQueryCreator;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DittoDetailRes {

    private DittoData dittoData;
    @Builder.Default
    private List<DittoCommentData> commentDataList = new ArrayList<>();

    private Integer commentCount;

    public static DittoDetailRes fromEntity(Ditto ditto, List<DittoComment> parentDittoComments, Integer commentCount, Boolean isMine, Long myBookmarkId, User reqUser) {
        DittoDetailRes dittoDetailRes = DittoDetailRes.builder()
                .dittoData(DittoData.fromEntity(ditto, isMine, myBookmarkId))
                .commentCount(commentCount)
                .build();

        dittoDetailRes.putParentCommentDataList(parentDittoComments, reqUser);
        return dittoDetailRes;
    }

    private void putParentCommentDataList(List<DittoComment> parentDittoComments, User reqUser) {
        for (DittoComment parentDittoComment : parentDittoComments) {
            this.commentDataList.add(DittoCommentData.parentFromEntity(parentDittoComment, reqUser));
        }
    }

}
