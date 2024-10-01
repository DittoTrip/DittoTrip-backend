package site.dittotrip.ditto_trip.quest.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class QuestListRes {

    private List<QuestData> questDataList;

}
