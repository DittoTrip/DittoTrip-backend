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
import site.dittotrip.ditto_trip.quest.repository.QuestRepository;
import site.dittotrip.ditto_trip.quest.repository.UserQuestRepository;
import site.dittotrip.ditto_trip.review.exception.NoAuthorityException;
import site.dittotrip.ditto_trip.reward.domain.Reward;
import site.dittotrip.ditto_trip.reward.domain.UserReward;
import site.dittotrip.ditto_trip.reward.repository.UserRewardRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final UserQuestRepository userQuestRepository;
    private final UserRewardRepository userRewardRepository;

    public UserQuestListRes findQuestList(User reqUser, UserQuestStatus userQuestStatus, Pageable pageable) {
        Page<UserQuest> page = userQuestRepository.findByUserAndUserQuestStatus(reqUser, userQuestStatus, pageable);
        return UserQuestListRes.fromEntities(page);
    }

    @Transactional(readOnly = false)
    public void achieveQuest(User reqUser, Long userQuestId) {
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
            userRewardRepository.save(new UserReward(reqUser, reward));
        }

        if (rewardExp != null) {
            reqUser.getUserProfile().addExp(rewardExp);
        }
    }

}
