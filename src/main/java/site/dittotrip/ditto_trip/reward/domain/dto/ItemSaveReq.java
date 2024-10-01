package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.reward.domain.Badge;
import site.dittotrip.ditto_trip.reward.domain.Item;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;

@Data
@NoArgsConstructor
public class ItemSaveReq {

    private String name;
    private ItemType itemType;

    public Item toEntity(String imagePath, String wearingImagePath) {
        return new Item(name, imagePath, wearingImagePath, itemType);
    }

}
