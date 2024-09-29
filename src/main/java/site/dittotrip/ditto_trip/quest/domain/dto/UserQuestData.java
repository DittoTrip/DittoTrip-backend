package site.dittotrip.ditto_trip.quest.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.quest.domain.Quest;
import site.dittotrip.ditto_trip.quest.domain.UserQuest;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.reward.domain.dto.RewardData;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;

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
    private UserQuestStatus userQuestStatus;

    private RewardType rewardType;
    private RewardData rewardData;
    private Integer rewardExp;

    public static List<UserQuestData> fromEntities(List<UserQuest> userQuests) {
        List<UserQuestData> userQuestDataList = new ArrayList<>();
        for (UserQuest userQuest : userQuests) {
            userQuestDataList.add(fromEntity(userQuest));
        }
        return userQuestDataList;
    }

    private static UserQuestData fromEntity(UserQuest userQuest) {
        Quest quest = userQuest.getQuest();
        return UserQuestData.builder()
                .userQuestId(userQuest.getId())
                .title(quest.getTitle())
                .body(quest.getBody())
                .conditionCount(quest.getConditionCount())
                .nowCount(userQuest.getNowCount())
                .userQuestStatus(userQuest.getUserQuestStatus())
                .rewardType(quest.getReward().getRewardType())
                .rewardData(RewardData.fromEntity(quest.getReward()))
                .rewardExp(quest.getRewardExp())
                .build();
    }

}
