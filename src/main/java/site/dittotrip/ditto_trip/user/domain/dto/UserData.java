package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.user.domain.User;

@Data
@Builder
public class UserData {

    private Long userId;
    private String nickname;

    public static UserData fromEntity(User user) {
        return UserData.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }

}
