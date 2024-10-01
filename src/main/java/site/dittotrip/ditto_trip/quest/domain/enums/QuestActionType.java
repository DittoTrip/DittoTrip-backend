package site.dittotrip.ditto_trip.quest.domain.enums;

import lombok.Getter;

@Getter
public enum QuestActionType {

    VISIT("visitSpot"),
    REVIEW("saveReview"),
    DITTO("saveDitto"),
    FOLLOWING("saveFollow"),
    SPOT_APPLY("spotApply");


    private final String methodName;

    QuestActionType(String methodName) {
        this.methodName = methodName;
    }

}
