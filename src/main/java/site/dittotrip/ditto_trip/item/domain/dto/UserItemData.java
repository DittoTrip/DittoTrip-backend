package site.dittotrip.ditto_trip.item.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.item.domain.Item;
import site.dittotrip.ditto_trip.item.domain.UserItem;
import site.dittotrip.ditto_trip.item.domain.enums.ItemType;

import java.time.LocalDateTime;

@Data
@Builder
public class UserItemData {

    private Long itemId;
    private String imagePath;
    private ItemType itemType;
    private LocalDateTime acquiredDateTime;

    public static UserItemData fromEntity(UserItem userItem) {
        return UserItemData.builder()
                .itemId(userItem.getItem().getId())
                .imagePath(userItem.getItem().getImagePath())
                .itemType(userItem.getItem().getItemType())
                .acquiredDateTime(userItem.getCreatedDateTime())
                .build();
    }

}
