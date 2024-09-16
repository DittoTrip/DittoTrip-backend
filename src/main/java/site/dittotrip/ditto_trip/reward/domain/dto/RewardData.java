package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.Reward;

@Data
@Builder
public class RewardData {

    private Long rewardId;
    private String name;
    private String imagePath;

    public static RewardData fromEntity(Reward reward) {
        if (reward == null) {
            return null;
        }
        return RewardData.builder()
                .rewardId(reward.getId())
                .name(reward.getName())
                .imagePath(reward.getImagePath())
                .build();
    }

}
