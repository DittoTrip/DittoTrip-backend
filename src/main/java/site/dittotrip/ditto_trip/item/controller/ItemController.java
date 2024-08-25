package site.dittotrip.ditto_trip.item.controller;

import site.dittotrip.ditto_trip.item.domain.dto.UserItemListRes;
import site.dittotrip.ditto_trip.item.service.ItemService;

/**
 * 1. 유저의 보유 아이템 리스트 조회
 */
public class ItemController {

    private ItemService itemService;

    public UserItemListRes UsersItemList(Long userId) {
        return itemService.findUsersItemList(userId);
    }

}
