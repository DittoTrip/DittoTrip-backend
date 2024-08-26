package site.dittotrip.ditto_trip.userpage.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoData;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoMiniData;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileData;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.util.List;

@Data
@Builder
public class UserPageRes {

    private UserData userData;
    private UserProfileData userProfileData;
    private List<DittoMiniData> dittoMiniDataList;

    private Integer followingCount;
    private Integer followedCount;

    public static UserPageRes fromEntities(User user, UserProfile userProfile, List<Ditto> dittos,
                                           Integer followingCount, Integer followedCount) {
        return UserPageRes.builder()
                .userData(UserData.fromEntity(user))
                .userProfileData(UserProfileData.fromEntity(userProfile))
                .dittoMiniDataList(DittoMiniData.listFromEntities(dittos, user))
                .followingCount(followingCount)
                .followedCount(followedCount)
                .build();
    }

}
