package site.dittotrip.ditto_trip.follow.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

@Data
@Builder
public class FollowData {

    private Long followId;
    private UserData userData;
    // UserProfile

    private Boolean isMine;

}
