package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.UserReward;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserBadgeListRes {

    List<UserBadgeData> userBadgeDataList = new ArrayList<>();

    public static UserBadgeListRes fromEntities(List<UserReward> userRewards) {
        UserBadgeListRes userBadgeListRes = new UserBadgeListRes();
        List<UserBadgeData> list = userBadgeListRes.getUserBadgeDataList();

        for (UserReward userReward : userRewards) {
            list.add(UserBadgeData.fromEntity(userReward));
        }

        return userBadgeListRes;
    }

}
