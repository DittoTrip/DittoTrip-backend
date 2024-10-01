package site.dittotrip.ditto_trip.quest.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.quest.domain.Quest;
import site.dittotrip.ditto_trip.quest.domain.enums.QuestActionType;
import site.dittotrip.ditto_trip.reward.domain.dto.RewardData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class QuestData {

    private Long questId;
    private String title;
    private String body;
    private Integer conditionCount;
    private QuestActionType questActionType;
    private LocalDateTime createdDateTime;

    private Integer rewardExp;
    private RewardData rewardData;

    public static List<QuestData> fromEntities(List<Quest> quests) {
        List<QuestData> questDataList = new ArrayList<>();
        for (Quest quest : quests) {
            questDataList.add(QuestData.fromEntity(quest));
        }

        return questDataList;
    }

    public static QuestData fromEntity(Quest quest) {
        return QuestData.builder()
                .questId(quest.getId())
                .title(quest.getTitle())
                .body(quest.getBody())
                .conditionCount(quest.getConditionCount())
                .questActionType(quest.getQuestActionType())
                .createdDateTime(quest.getCreatedDateTime())
                .rewardExp(quest.getRewardExp())
                .rewardData(RewardData.fromEntity(quest.getReward()))
                .build();
    }

}
