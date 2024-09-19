package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.dittotrip.ditto_trip.reward.domain.Item;
import site.dittotrip.ditto_trip.reward.domain.UserItem;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;

import java.time.LocalDateTime;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserItemData {

    private Long userRewardId;
    private String name;
    private String imagePath;
    private ItemType itemType;
    private LocalDateTime createdDateTime;

    public static UserItemData fromEntity(UserItem userItem) {
        if (userItem == null) {
            return null;
        }

        Item item = userItem.getItem();
        return UserItemData.builder()
                .userRewardId(userItem.getId())
                .name(item.getName())
                .imagePath(item.getImagePath())
                .itemType(item.getItemType())
                .createdDateTime(userItem.getCreatedDateTime())
                .build();
    }

}
