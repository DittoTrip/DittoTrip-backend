package site.dittotrip.ditto_trip.profile.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.reward.domain.Badge;
import site.dittotrip.ditto_trip.reward.domain.Item;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;

@Data
@Builder
public class UserProfileData {

    private Integer progressionBar;
    private Item itemSkin;
    private Item itemEyes;
    private Item itemMouse;
    private Item itemHair;
    private Item itemAccessory;
    private Badge badge;

    public static UserProfileData fromEntity(UserProfile userProfile) {
        return UserProfileData.builder()
                .progressionBar(userProfile.getProgressionBar())
                .itemSkin(userProfile.getItemSkin())
                .itemEyes(userProfile.getItemEyes())
                .itemMouse(userProfile.getItemMouse())
                .itemHair(userProfile.getItemHair())
                .itemAccessory(userProfile.getItemAccessory())
                .badge(userProfile.getBadge())
                .build();
    }

}
