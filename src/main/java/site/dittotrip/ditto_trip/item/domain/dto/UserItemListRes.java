package site.dittotrip.ditto_trip.item.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.item.domain.UserBadge;
import site.dittotrip.ditto_trip.item.domain.UserItem;
import site.dittotrip.ditto_trip.item.domain.enums.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserItemListRes {

    Map<ItemType, List<UserItemData>> userItemDataMap = new HashMap<>();

    public static UserItemListRes fromEntities(List<UserItem> userItems) {
        UserItemListRes userItemListRes = new UserItemListRes();
        Map<ItemType, List<UserItemData>> map = userItemListRes.getUserItemDataMap();

        ItemType[] types = ItemType.values();
        for (ItemType type : types) {
            map.put(type, new ArrayList<>());
        }

        for (UserItem userItem : userItems) {
            map.get(userItem.getItem().getItemType()).add(UserItemData.fromEntity(userItem));
        }

        return userItemListRes;
    }

}
