package site.dittotrip.ditto_trip.profile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.profile.domain.dto.PasswordModifyReq;
import site.dittotrip.ditto_trip.profile.domain.dto.UserBadgeModifyReq;
import site.dittotrip.ditto_trip.profile.domain.dto.UserNicknameModifyReq;
import site.dittotrip.ditto_trip.exception.common.NoAuthorityException;
import site.dittotrip.ditto_trip.reward.domain.*;
import site.dittotrip.ditto_trip.reward.domain.enums.ItemType;
import site.dittotrip.ditto_trip.reward.repository.*;
import site.dittotrip.ditto_trip.profile.domain.UserProfile;
import site.dittotrip.ditto_trip.profile.domain.dto.UserProfileModifyReq;
import site.dittotrip.ditto_trip.reward.exception.NotMatchedItemTypeException;
import site.dittotrip.ditto_trip.profile.repository.UserProfileRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserItemRepository userItemRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final PasswordEncoder passwordEncoder;


    public void modifyUserNickname(Long reqUserId, UserNicknameModifyReq modifyReq) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        reqUser.setNickname(modifyReq.getNickname());
    }

    public void modifyPassword(Long reqUserId, PasswordModifyReq modifyReq) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        if (!passwordEncoder.matches(modifyReq.getOriginPassword(), reqUser.getPassword())) {
            throw new IllegalArgumentException("Password doesn't match");
        }
        // todo 비번 유효성 검사
        reqUser.setPassword(passwordEncoder.encode(modifyReq.getNewPassword()));
    }

    public void modifyUserItem(Long reqUserId, UserProfileModifyReq modifyReq) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        UserProfile userProfile = userProfileRepository.findByUser(reqUser).orElseThrow(NoSuchElementException::new);

        UserItem userItemSkin = userItemRepository.findById(modifyReq.getUserItemSkinId()).orElseThrow(NoSuchElementException::new);
        UserItem userItemEyes = userItemRepository.findById(modifyReq.getUserItemEyesId()).orElseThrow(NoSuchElementException::new);
        UserItem userItemMouse = userItemRepository.findById(modifyReq.getUserItemMouseId()).orElseThrow(NoSuchElementException::new);
        UserItem userItemHair = userItemRepository.findById(modifyReq.getUserItemHairId()).orElseThrow(NoSuchElementException::new);
        UserItem userItemAccessory = userItemRepository.findById(modifyReq.getUserItemAccessoryId()).orElseThrow(NoSuchElementException::new);

        validateItemType(userItemSkin, userItemEyes, userItemMouse, userItemHair, userItemAccessory);
        validateItemOwner(reqUser, userItemSkin, userItemEyes, userItemMouse, userItemHair, userItemAccessory);

        userProfile.setUserItemSkin(userItemSkin);
        userProfile.setUserItemEyes(userItemEyes);
        userProfile.setUserItemMouse(userItemMouse);
        userProfile.setUserItemHair(userItemHair);
        userProfile.setUserItemAccessory(userItemAccessory);
    }

    public void modifyUserBadge(Long reqUserId, UserBadgeModifyReq modifyReq) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        UserBadge userBadge = userBadgeRepository.findById(modifyReq.getUserBadgeId()).orElseThrow(NoSuchElementException::new);

        if (!reqUser.equals(userBadge.getUser())) {
            throw new NoAuthorityException();
        }

        reqUser.getUserProfile().setUserBadge(userBadge);
    }

    private void validateItemType(UserItem userItemSkin, UserItem userItemEyes, UserItem userItemMouse, UserItem userItemHair, UserItem userItemAccessory) {
        if (!userItemSkin.getItem().getItemType().equals(ItemType.SKIN) ||
                !userItemEyes.getItem().getItemType().equals(ItemType.EYES) ||
                !userItemMouse.getItem().getItemType().equals(ItemType.MOUSE) ||
                !userItemHair.getItem().getItemType().equals(ItemType.HAIR) ||
                !userItemAccessory.getItem().getItemType().equals(ItemType.ACCESSORY)) {
            throw new NotMatchedItemTypeException();
        }
    }

    private void validateItemOwner(User reqUser, UserItem userItemSkin, UserItem userItemEyes, UserItem userItemMouse, UserItem userItemHair, UserItem userItemAccessory) {
        if (!reqUser.equals(userItemSkin.getUser()) ||
                !reqUser.equals(userItemEyes.getUser()) ||
                !reqUser.equals(userItemMouse.getUser()) ||
                !reqUser.equals(userItemHair.getUser()) ||
                !reqUser.equals(userItemAccessory.getUser())) {
            throw new NoAuthorityException();
        }
    }

}
