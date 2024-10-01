package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.dittotrip.ditto_trip.reward.domain.Badge;
import site.dittotrip.ditto_trip.reward.domain.enums.RewardType;

@Data
@NoArgsConstructor
public class BadgeSaveReq {

    private String name;
    private String body;
    private String conditionBody;

    public Badge toEntity(String imagePath) {
        return new Badge(name, imagePath, body, conditionBody);
    }

}
