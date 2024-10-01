package site.dittotrip.ditto_trip.reward.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.reward.domain.*;
import site.dittotrip.ditto_trip.reward.domain.dto.*;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;
import site.dittotrip.ditto_trip.reward.repository.*;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.S3Service;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RewardService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final UserItemRepository userItemRepository;
    private final UserBadgeRepository userBadgeRepository;
    private final ItemRepository itemRepository;
    private final S3Service s3Service;

    public UserItemListRes findUsersItemList(Long reqUserId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        List<UserItem> userItems = userItemRepository.findByUser(reqUser);
        return UserItemListRes.fromEntities(reqUser, userItems);
    }

    public UserBadgeListRes findBadgeList(Long reqUserId, Long userId) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);

        List<Badge> badges = badgeRepository.findAll();
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);

        if (reqUser.equals(user)) {
            Map<Reward, UserBadge> ownBadgeMap = new HashMap<>();
            for (UserBadge userBadge : userBadges) {
                ownBadgeMap.put(userBadge.getBadge(), userBadge);
            }
            return UserBadgeListRes.fromEntitiesAtMine(user, badges, ownBadgeMap);
        } else {
            return UserBadgeListRes.fromEntities(badges);
        }
    }

    public ItemListRes findItemList() {
        List<Item> items = itemRepository.findAll();
        List<ItemData> itemDataList = ItemData.fromEntities(items);
        return ItemListRes.builder()
                .itemDataList(itemDataList)
                .build();
    }

    @Transactional(readOnly = false)
    public void saveBadge(BadgeSaveReq saveReq, MultipartFile multipartFile) {
        // image 처리
        String imagePath = s3Service.uploadFile(multipartFile);
        Badge badge = saveReq.toEntity(imagePath);

        badgeRepository.save(badge);
    }

    @Transactional(readOnly = false)
    public void saveItem(ItemSaveReq saveReq, MultipartFile multipartFile, MultipartFile multipartFile2) {
        // image 처리
        String imagePath = s3Service.uploadFile(multipartFile);
        String imagePath2 = s3Service.uploadFile(multipartFile2);

        Item item = saveReq.toEntity(imagePath, imagePath2);

        itemRepository.save(item);
    }

}
