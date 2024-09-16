package site.dittotrip.ditto_trip.follow.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileData;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

@Data
@Builder
public class FollowData {

    private Long followId;
    private UserData userData;
    private UserProfileData userProfileData;

    private Boolean isMine;

    public static FollowData followingListFromEntity(Follow follow, User reqUser) {
        User followedUser = follow.getFollowedUser();
        FollowData followData = FollowData.builder()
                .followId(follow.getId())
                .userData(UserData.fromEntity(followedUser))
                .userProfileData(UserProfileData.fromEntity(followedUser.getUserProfile()))
                .build();

        if (reqUser != null && followedUser.getId() == reqUser.getId()) {
            followData.setIsMine(Boolean.TRUE);
        } else {
            followData.setIsMine(Boolean.FALSE);
        }

        return followData;
    }

    public static FollowData followedListFromEntity(Follow follow, User reqUser) {
        User followingUser = follow.getFollowingUser();

        FollowData followData = FollowData.builder()
                .followId(follow.getId())
                .userData(UserData.fromEntity(followingUser))
                .userProfileData(UserProfileData.fromEntity(followingUser.getUserProfile()))
                .build();

        if (reqUser != null && followingUser.getId() == reqUser.getId()) {
            followData.setIsMine(Boolean.TRUE);
        } else {
            followData.setIsMine(Boolean.FALSE);
        }

        return followData;
    }

}
