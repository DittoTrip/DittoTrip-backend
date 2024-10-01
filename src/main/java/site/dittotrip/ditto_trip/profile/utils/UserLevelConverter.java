package site.dittotrip.ditto_trip.profile.utils;

import site.dittotrip.ditto_trip.reward.domain.Reward;

import java.util.Iterator;
import java.util.Map;

public class UserLevelConverter {

    private static final Map<Integer, Integer> LEVEL_MAP = Map.of(
            0, 1,
            100, 2,
            300, 3,
            700, 4,
            1400, 5,
            2400, 6,
            3800, 7,
            5700, 8,
            8200, 9,
            11400, 10
    );

    public static final Map<Integer, String> REWARD_MAP = Map.of(
            2, "여행의 발걸음",
            3, "여행의 이야기꾼",
            4, "여행의 도전자",
            5, "여행의 길잡이",
            6, "여행의 탐험가",
            7, "여행의 대가",
            8, "여행의 정복자",
            9, "여행의 영웅",
            10, "여행의 전설"
            );

    public static int getLevel(int exp) {
        int level = 0;

        for (Integer mapExp : LEVEL_MAP.keySet()) {
            if (mapExp <= exp) {
                level = LEVEL_MAP.get(mapExp);
            } else {
                break;
            }
        }

        return level;
    }

}
