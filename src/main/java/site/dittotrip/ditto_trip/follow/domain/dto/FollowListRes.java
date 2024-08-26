package site.dittotrip.ditto_trip.follow.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.follow.domain.Follow;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class FollowListRes {

    private Integer followCount;
    private List<FollowData> followDataList = new ArrayList<>();

    public static FollowListRes fromEntities(List<Follow> follows, User reqUser, Boolean isFollowingList) {
        FollowListRes followListRes = FollowListRes.builder()
                .followCount(follows.size())
                .build();

        List<FollowData> followDataList = followListRes.getFollowDataList();

        for (Follow follow : follows) {
            if (isFollowingList) {
                followDataList.add(FollowData.followingListFromEntity(follow, reqUser));
            } else {
                followDataList.add(FollowData.followedListFromEntity(follow, reqUser));
            }
        }

        return followListRes;
    }

}
