package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.UserReward;

import java.time.LocalDateTime;

@Data
@Builder
public class UserBadgeData {

    private Long rewardId;
    private String imagePath;
    private LocalDateTime createdDateTime;

    public static UserBadgeData fromEntity(UserReward userReward) {
        return UserBadgeData.builder()
                .rewardId(userReward.getReward().getId())
                .imagePath(userReward.getReward().getImagePath())
                .createdDateTime(userReward.getCreatedDateTime())
                .build();
    }

}
