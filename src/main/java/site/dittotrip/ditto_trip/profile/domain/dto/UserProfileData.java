package site.dittotrip.ditto_trip.profile.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.reward.domain.dto.BadgeData;
import site.dittotrip.ditto_trip.reward.domain.dto.UserItemData;

@Data
@Builder
public class UserProfileData {

    private ProgressionData progressionData;
    private UserItemData itemSkin;
    private UserItemData itemEyes;
    private UserItemData itemMouse;
    private UserItemData itemHair;
    private UserItemData itemAccessory;
    private BadgeData badgeData;

    public static UserProfileData fromEntity(UserProfile userProfile) {
        return UserProfileData.builder()
                .progressionData(ProgressionData.fromEntity(userProfile.getProgressionBar()))
                .itemSkin(UserItemData.fromEntity(userProfile.getUserItemSkin()))
                .itemEyes(UserItemData.fromEntity(userProfile.getUserItemEyes()))
                .itemMouse(UserItemData.fromEntity(userProfile.getUserItemMouse()))
                .itemHair(UserItemData.fromEntity(userProfile.getUserItemHair()))
                .itemAccessory(UserItemData.fromEntity(userProfile.getUserItemAccessory()))
                .badgeData(BadgeData.fromEntity(userProfile))
                .build();
    }

}
