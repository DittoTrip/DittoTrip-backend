package site.dittotrip.ditto_trip.quest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.quest.domain.Quest;
import site.dittotrip.ditto_trip.quest.domain.UserQuest;
import site.dittotrip.ditto_trip.quest.domain.dto.UserQuestListRes;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.quest.exception.AlreadyAchieveQuestException;
import site.dittotrip.ditto_trip.quest.exception.NotAchieveQuestException;
import site.dittotrip.ditto_trip.quest.repository.UserQuestRepository;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.reward.domain.*;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;
import site.dittotrip.ditto_trip.reward.repository.*;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestService {

    private final UserRepository userRepository;
    private final UserQuestRepository userQuestRepository;
    private final ItemRepository itemRepository;
    private final BadgeRepository badgeRepository;
    private final UserItemRepository userItemRepository;
    private final UserBadgeRepository userBadgeRepository;

    public UserQuestListRes findQuestList(Long reqUserId, UserQuestStatus userQuestStatus, Pageable pageable) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        Page<UserQuest> page = userQuestRepository.findByUserAndUserQuestStatus(reqUser, userQuestStatus, pageable);
        return UserQuestListRes.fromEntities(page);
    }

    @Transactional(readOnly = false)
    public void achieveQuest(Long reqUserId, Long userQuestId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        UserQuest userQuest = userQuestRepository.findById(userQuestId).orElseThrow(NoSuchElementException::new);

        if (reqUser.getId() != userQuest.getUser().getId()) {
            throw new NoAuthorityException();
        }

        if (userQuest.getUserQuestStatus().equals(UserQuestStatus.NOT_ACHIEVE)) {
            throw new NotAchieveQuestException();
        }

        if (userQuest.getUserQuestStatus().equals(UserQuestStatus.ACHIEVE)) {
            throw new AlreadyAchieveQuestException();
        }

        userQuest.achieveQuest();

        // 보상 처리
        Quest quest = userQuest.getQuest();
        Reward reward = quest.getReward();
        Integer rewardExp = quest.getRewardExp();

        if (reward != null) {
            if (reward.getRewardType().equals(RewardType.ITEM)) {
                Item item = itemRepository.findById(reward.getId()).get();
                userItemRepository.save(new UserItem(reqUser, item));
            } else {
                Badge badge = badgeRepository.findById(reward.getId()).get();
                userBadgeRepository.save(new UserBadge(reqUser, badge));
            }
        }

        if (rewardExp != null) {
            reqUser.getUserProfile().addExp(rewardExp);
        }
    }

}
