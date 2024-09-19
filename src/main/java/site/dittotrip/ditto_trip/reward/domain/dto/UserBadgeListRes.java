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

    private List<BadgeData> badgeDataList = new ArrayList<>();

    private Boolean isMine;

    public static UserBadgeListRes fromEntitiesAtMine(List<Badge> badges, Map<Reward, UserBadge> ownBadgeMap) {
        UserBadgeListRes userBadgeListRes = new UserBadgeListRes();
        userBadgeListRes.setIsMine(Boolean.TRUE);

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

    public static UserBadgeListRes fromEntities(List<Badge> badges) {
        UserBadgeListRes userBadgeListRes = new UserBadgeListRes();
        userBadgeListRes.setIsMine(Boolean.FALSE);

        List<BadgeData> badgeDataList = userBadgeListRes.getBadgeDataList();

        for (Badge badge : badges) {
            badgeDataList.add(BadgeData.fromEntity(badge, null));
        }

        return userBadgeListRes;
    }

}
