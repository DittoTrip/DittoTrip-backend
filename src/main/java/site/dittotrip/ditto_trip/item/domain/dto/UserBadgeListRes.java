package site.dittotrip.ditto_trip.item.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.item.domain.UserBadge;
import site.dittotrip.ditto_trip.item.domain.UserItem;
import site.dittotrip.ditto_trip.item.domain.enums.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
