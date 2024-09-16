package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.Badge;
import site.dittotrip.ditto_trip.reward.domain.Reward;
import site.dittotrip.ditto_trip.reward.domain.UserBadge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class UserBadgeListRes {

    List<BadgeData> badgeDataList = new ArrayList<>();

    public static UserBadgeListRes fromEntities(List<Badge> badges, Map<Reward, UserBadge> ownBadgeMap) {
        UserBadgeListRes userBadgeListRes = new UserBadgeListRes();
        List<BadgeData> badgeDataList = userBadgeListRes.getBadgeDataList();

        for (Badge badge : badges) {
            UserBadge userBadge = null;
            if (ownBadgeMap.containsKey(badge)) {
                userBadge = ownBadgeMap.get(badge);
            }
            badgeDataList.add(BadgeData.fromEntity(badge, userBadge));
        }

        return userBadgeListRes;
    }

}
