package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.UserBadge;

import java.time.LocalDateTime;

@Data
@Builder
public class UserBadgeData {

    private Long badgeId;
    private String imagePath;
    private LocalDateTime acquiredDateTime;

    public static UserBadgeData fromEntity(UserBadge userBadge) {
        return UserBadgeData.builder()
                .badgeId(userBadge.getBadge().getId())
                .imagePath(userBadge.getBadge().getImagePath())
                .acquiredDateTime(userBadge.getCreatedDateTime())
                .build();
    }

}
