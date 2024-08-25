package site.dittotrip.ditto_trip.userpage.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileData;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

@Data
@Builder
public class UserPageRes {

    private UserData userData;
    private UserProfileData userProfileData;

//    private Integer followedCount;
//    private Integer followingCount;
//    private List<DittoData> dittoData;

}
