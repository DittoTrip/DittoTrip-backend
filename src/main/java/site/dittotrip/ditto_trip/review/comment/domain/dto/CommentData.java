package site.dittotrip.ditto_trip.review.comment.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentData {

    // 유저 데이터
    private String body;
    private LocalDateTime createdDateTime;

}
