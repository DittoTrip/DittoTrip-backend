package site.dittotrip.ditto_trip.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.item.domain.UserBadge;
import site.dittotrip.ditto_trip.item.domain.UserItem;
import site.dittotrip.ditto_trip.item.domain.dto.UserBadgeListRes;
import site.dittotrip.ditto_trip.item.domain.dto.UserItemListRes;
import site.dittotrip.ditto_trip.item.repository.ItemRepository;
import site.dittotrip.ditto_trip.item.repository.UserBadgeRepository;
import site.dittotrip.ditto_trip.item.repository.UserItemRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final UserRepository userRepository;
    private final UserItemRepository userItemRepository;
    private final UserBadgeRepository userBadgeRepository;

    public UserItemListRes findUsersItemList(User user) {
        List<UserItem> userItems = userItemRepository.findByUser(user);

        return UserItemListRes.fromEntities(userItems);
    }

    public UserBadgeListRes findUsersBadgeList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);

        return UserBadgeListRes.fromEntities(userBadges);
    }

}
