package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.Item;
import site.dittotrip.ditto_trip.reward.domain.Reward;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ItemData {

    private Long rewardId;
    private String name;
    private String imagePath;
    private ItemType itemType;

    public static List<ItemData> fromEntities(List<Item> items) {
        List<ItemData> itemDataList = new ArrayList<>();
        for (Item item : items) {
            itemDataList.add(ItemData.fromEntity(item));
        }
        return itemDataList;
    }

    public static ItemData fromEntity(Reward reward) {
        if (reward == null) {
            return null;
        }
        return ItemData.builder()
                .rewardId(reward.getId())
                .name(reward.getName())
                .imagePath(reward.getImagePath())
                .itemType(((Item) reward).getItemType())
                .build();
    }

}
