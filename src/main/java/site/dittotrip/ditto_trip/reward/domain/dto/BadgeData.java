package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.Badge;
import site.dittotrip.ditto_trip.reward.domain.UserBadge;

import java.time.LocalDateTime;

@Data
@Builder
public class BadgeData {

    private Long rewardId;
    private String name;
    private String body;
    private String conditionBody;
    private String imagePath;
    private LocalDateTime createdDateTime;

    private Long userBadgeId;

    public static BadgeData fromEntity(Badge badge, UserBadge userBadge) {
        BadgeData badgeData = BadgeData.builder()
                .rewardId(badge.getId())
                .name(badge.getName())
                .body(badge.getBody())
                .conditionBody(badge.getConditionBody())
                .imagePath(badge.getImagePath())
                .createdDateTime(badge.getCreatedDateTime())
                .build();

        if (userBadge != null) {
            badgeData.setUserBadgeId(userBadge.getId());
        }

        return badgeData;
    }

}
