package site.dittotrip.ditto_trip.quest.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.quest.domain.UserQuest;
import site.dittotrip.ditto_trip.quest.domain.enums.QuestRewardType;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.reward.domain.dto.RewardData;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserQuestData {

    private Long userQuestId;
    private String title;
    private String body;
    private Integer conditionCount;
    private Integer nowCount;
    private QuestRewardType questRewardType;
    private UserQuestStatus userQuestStatus;

    private RewardData rewardData;

    public static List<UserQuestData> fromEntities(List<UserQuest> userQuests) {
        List<UserQuestData> userQuestDataList = new ArrayList<>();
        for (UserQuest userQuest : userQuests) {
            userQuestDataList.add(fromEntity(userQuest));
        }
        return userQuestDataList;
    }

    private static UserQuestData fromEntity(UserQuest userQuest) {
        return UserQuestData.builder()
                .userQuestId(userQuest.getId())
                .title(userQuest.getQuest().getTitle())
                .body(userQuest.getQuest().getBody())
                .conditionCount(userQuest.getQuest().getConditionCount())
                .nowCount(userQuest.getNowCount())
                .questRewardType(userQuest.getQuest().getQuestRewardType())
                .userQuestStatus(userQuest.getUserQuestStatus())
                .rewardData(RewardData.fromEntity(userQuest.getQuest().getReward()))
                .build();
    }

}
