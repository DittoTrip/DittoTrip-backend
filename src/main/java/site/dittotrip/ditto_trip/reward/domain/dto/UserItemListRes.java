package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileData;
import site.dittotrip.ditto_trip.reward.domain.Item;
import site.dittotrip.ditto_trip.reward.domain.UserItem;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class UserItemListRes {

    private UserProfileData userProfileData;
    private Map<ItemType, List<UserItemData>> userItemDataMap = new HashMap<>();

    public static UserItemListRes fromEntities(User user, List<UserItem> userItems) {
        UserItemListRes userItemListRes = new UserItemListRes();
        userItemListRes.setUserProfileData(UserProfileData.fromEntity(user.getUserProfile()));
        Map<ItemType, List<UserItemData>> map = userItemListRes.getUserItemDataMap();

        ItemType[] types = ItemType.values();
        for (ItemType type : types) {
            map.put(type, new ArrayList<>());
        }

        for (UserItem userItem : userItems) {
            Item item = userItem.getItem();
            map.get(item.getItemType()).add(UserItemData.fromEntity(userItem));
        }

        return userItemListRes;
    }

}
