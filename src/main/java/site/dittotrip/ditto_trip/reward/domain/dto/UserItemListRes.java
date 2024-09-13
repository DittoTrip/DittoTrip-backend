package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.Item;
import site.dittotrip.ditto_trip.reward.domain.UserReward;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserItemListRes {

    Map<ItemType, List<UserItemData>> userItemDataMap = new HashMap<>();

    public static UserItemListRes fromEntities(List<UserReward> userRewards) {
        UserItemListRes userItemListRes = new UserItemListRes();
        Map<ItemType, List<UserItemData>> map = userItemListRes.getUserItemDataMap();

        ItemType[] types = ItemType.values();
        for (ItemType type : types) {
            map.put(type, new ArrayList<>());
        }

        for (UserReward userReward : userRewards) {
            map.get(((Item) userReward.getReward()).getItemType()).add(UserItemData.fromEntity(userReward));
        }

        return userItemListRes;
    }

}
