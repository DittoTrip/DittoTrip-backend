package site.dittotrip.ditto_trip.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileData;
import site.dittotrip.ditto_trip.user.domain.User;

@Data
@AllArgsConstructor
public class MyUserInfoRes {

    private MyUserInfoData myUserInfoData;

    public static MyUserInfoRes fromEntity(User user) {
        return new MyUserInfoRes(MyUserInfoData.fromEntity(user));
    }

}
