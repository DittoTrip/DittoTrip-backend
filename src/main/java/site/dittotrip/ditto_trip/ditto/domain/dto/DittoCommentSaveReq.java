package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.user.domain.User;

import java.time.LocalDateTime;

@Data
public class DittoCommentSaveReq {

    private String body;

    public DittoComment toEntity(Ditto ditto, User user, DittoComment parentComment) {
        return new DittoComment(body, LocalDateTime.now(), user, ditto, parentComment);
    }

    public void modifyEntity(DittoComment dittoComment) {
        dittoComment.setBody(body);
    }

}
