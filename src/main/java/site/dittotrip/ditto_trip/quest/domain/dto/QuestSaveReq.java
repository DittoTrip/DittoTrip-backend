package site.dittotrip.ditto_trip.quest.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.quest.domain.Quest;
import site.dittotrip.ditto_trip.quest.domain.enums.QuestActionType;
import site.dittotrip.ditto_trip.reward.domain.Reward;

@Data
@NoArgsConstructor
public class QuestSaveReq {

    private String title;
    private String body;
    private Integer conditionCount;
    private QuestActionType questActionType;

    private Integer rewardExp;
    private Integer rewardId;

    public Quest toEntity(Reward reward) {
        return new Quest(title, body, conditionCount, questActionType,
                rewardExp, reward);
    }

}
