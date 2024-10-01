package site.dittotrip.ditto_trip.profile.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 기획 확정 후 Map 수정
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressionData {

    private static final Map<Integer, Integer> REQUIRED_EXP_LIST = new HashMap<>(Map.of(
            1, 100, 2, 200, 3, 400, 4, 700, 5, 1000,
            6, 1400, 7, 1900, 8, 2500, 9, 3200, 10, 4000));
    private static final Map<Integer, String> LEVEL_NAME_MAP = new HashMap<>(Map.of(
            1, "여행의 새싹",
            2, "여행의 발걸음",
            3, "여행의 이야기꾼",
            4, "여행의 도전자",
            5, "여행의 길잡이",
            6, "여행의 탐험가",
            7, "여행의 대가",
            8, "여행의 정복자",
            9, "여행의 영웅",
            10, "여행의 전설"
    ));

    private Integer presentExp;
    private Integer requiredExp;
    private String presentLevel;
    private String nextLevel;
    private Double progressionRate;

    public static ProgressionData fromEntity(Integer userExp) {
        int presentLevelInt = 1;
        for (Integer exp : REQUIRED_EXP_LIST.values()) {
            if (userExp >= exp) {
                presentLevelInt += 1;
                userExp -= exp;
            }
        }

        ProgressionData progressionData = ProgressionData.builder()
                .presentExp(userExp)
                .requiredExp(REQUIRED_EXP_LIST.get(presentLevelInt))
                .presentLevel(convertLevelToString(presentLevelInt))
                .nextLevel(convertLevelToString(presentLevelInt + 1))
                .build();

        progressionData.setProgressionRate(
                (double) progressionData.getPresentExp() / (double) progressionData.getRequiredExp()
        );

        return progressionData;
    }

    private static String convertLevelToString(int level) {
        if (level > 12) {
            return null;
        }
        return "LV" + level + " " + LEVEL_NAME_MAP.get(level);
    }

}
