package site.dittotrip.ditto_trip.item.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.item.domain.UserBadge;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserBadgeListRes {

    List<UserBadgeData> userBadgeDataList = new ArrayList<>();

    public static UserBadgeListRes fromEntities(List<UserBadge> userBadges) {
        UserBadgeListRes userBadgeListRes = new UserBadgeListRes();
        List<UserBadgeData> list = userBadgeListRes.getUserBadgeDataList();

        for (UserBadge userBadge : userBadges) {
            list.add(UserBadgeData.fromEntity(userBadge));
        }

        return userBadgeListRes;
    }

}
