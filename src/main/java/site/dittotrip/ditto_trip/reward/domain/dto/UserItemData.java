package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.Item;
import site.dittotrip.ditto_trip.reward.domain.UserReward;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;

import java.time.LocalDateTime;

@Data
@Builder
public class UserItemData {

    private Long userRewardId;
    private String imagePath;
    private ItemType itemType;
    private LocalDateTime createdDateTime;

    public static UserItemData fromEntity(UserReward userReward) {
        return UserItemData.builder()
                .userRewardId(userReward.getId())
                .imagePath(userReward.getReward().getImagePath())
                .itemType(((Item) userReward.getReward()).getItemType())
                .createdDateTime(userReward.getCreatedDateTime())
                .build();
    }

}
