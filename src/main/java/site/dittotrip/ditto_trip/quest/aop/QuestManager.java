package site.dittotrip.ditto_trip.quest.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.alarm.domain.Alarm;
import site.dittotrip.ditto_trip.alarm.repository.AlarmRepository;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.quest.domain.Quest;
import site.dittotrip.ditto_trip.quest.domain.UserQuest;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.quest.exception.TooManyNowCountException;
import site.dittotrip.ditto_trip.quest.repository.UserQuestRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class QuestManager {

    private final UserQuestRepository userQuestRepository;
    private final AlarmRepository alarmRepository;

    public void handleQuests(@AuthenticationPrincipal CustomUserDetails userDetails,
                             String methodName) {
        User user = CustomUserDetails.getUserFromUserDetails(userDetails, true);

        List<UserQuest> notAchievedUserQuests = userQuestRepository.findByUserAndUserQuestStatus(user, UserQuestStatus.NOT_ACHIEVE);
        for (UserQuest userQuest : notAchievedUserQuests) {
            Quest quest = userQuest.getQuest();
            if (!methodName.equals(quest.getQuestActionType().getMethodName())) {
                continue;
            }

            userQuest.addNowCount();
            if (userQuest.getNowCount() > quest.getConditionCount()) {
                throw new TooManyNowCountException();
            }

            if (userQuest.getNowCount() == quest.getConditionCount()) {
                userQuest.achieveButPendingQuest();
                processAlarmInHandlingQuest(userQuest);
            }
        }
    }

    private void processAlarmInHandlingQuest(UserQuest userQuest) {
        User user = userQuest.getUser();
        Quest quest = userQuest.getQuest();

        String title = "퀘스트를 달성했어요. 보상을 받으세요 !!";
        String body = "";
        String path = "/quest/list";
        List<User> targets = new ArrayList<>(List.of(user));

        alarmRepository.saveAll(Alarm.createAlarms(title, body, path, targets));
    }

}
