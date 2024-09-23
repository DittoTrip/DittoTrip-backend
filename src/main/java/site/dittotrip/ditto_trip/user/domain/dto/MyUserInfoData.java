package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileData;
import site.dittotrip.ditto_trip.user.domain.User;

@Data
@Builder
public class MyUserInfoData {

    private String nickname;
    private String email;
    private UserProfileData userProfileData;

    public static MyUserInfoData fromEntity(User user) {
        return MyUserInfoData.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .userProfileData(UserProfileData.fromEntity(user.getUserProfile()))
                .build();
    }

}
