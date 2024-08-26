package site.dittotrip.ditto_trip.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.item.domain.Badge;
import site.dittotrip.ditto_trip.item.domain.Item;
import site.dittotrip.ditto_trip.item.domain.enums.ItemType;
import site.dittotrip.ditto_trip.item.repository.BadgeRepository;
import site.dittotrip.ditto_trip.item.repository.ItemRepository;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileModifyReq;
import site.dittotrip.ditto_trip.item.exception.NotMatchedItemTypeException;
import site.dittotrip.ditto_trip.profile.repository.UserProfileRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserProfileService {

    private UserProfileRepository userProfileRepository;
    private ItemRepository itemRepository;
    private BadgeRepository badgeRepository;


    public void modifyUserProfile(User user, UserProfileModifyReq modifyReq) {
        UserProfile userProfile = userProfileRepository.findByUser(user).orElseThrow(NoSuchElementException::new);

        Item itemSkin = itemRepository.findById(modifyReq.getItemSkinId()).orElseThrow(NoSuchElementException::new);
        if (!itemSkin.getItemType().equals(ItemType.SKIN)) {
            throw new NotMatchedItemTypeException();
        }
        Item itemEyes = itemRepository.findById(modifyReq.getItemEyesId()).orElseThrow(NoSuchElementException::new);
        if (!itemEyes.getItemType().equals(ItemType.EYES)) {
            throw new NotMatchedItemTypeException();
        }
        Item itemMouse = itemRepository.findById(modifyReq.getItemMouseId()).orElseThrow(NoSuchElementException::new);
        if (!itemMouse.getItemType().equals(ItemType.MOUSE)) {
            throw new NotMatchedItemTypeException();
        }
        Item itemHair = itemRepository.findById(modifyReq.getItemHairId()).orElseThrow(NoSuchElementException::new);
        if (!itemHair.getItemType().equals(ItemType.HAIR)) {
            throw new NotMatchedItemTypeException();
        }
        Item itemAccessory = itemRepository.findById(modifyReq.getItemAccessoryId()).orElseThrow(NoSuchElementException::new);
        if (!itemAccessory.getItemType().equals(ItemType.ACCESSORY)) {
            throw new NotMatchedItemTypeException();
        }
        Badge badge = badgeRepository.findById(modifyReq.getBadgeId()).orElseThrow(NoSuchElementException::new);

        userProfile.setItemSkin(itemSkin);
        userProfile.setItemEyes(itemEyes);
        userProfile.setItemMouse(itemMouse);
        userProfile.setItemHair(itemHair);
        userProfile.setItemAccessory(itemAccessory);
        userProfile.setBadge(badge);
    }

}
