package site.dittotrip.ditto_trip.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.item.domain.UserBadge;
import site.dittotrip.ditto_trip.item.domain.UserItem;
import site.dittotrip.ditto_trip.item.domain.dto.UserItemListRes;
import site.dittotrip.ditto_trip.item.repository.ItemRepository;
import site.dittotrip.ditto_trip.item.repository.UserBadgeRepository;
import site.dittotrip.ditto_trip.item.repository.UserItemRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private UserRepository userRepository;+
    private UserItemRepository userItemRepository;
    private UserBadgeRepository userBadgeRepository;

    public UserItemListRes findUsersItemList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<UserItem> userItems = userItemRepository.findByUser(user);
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);

        return UserItemListRes.fromEntities(userItems, userBadges);
    }

}
