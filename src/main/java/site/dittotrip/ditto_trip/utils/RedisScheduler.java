package site.dittotrip.ditto_trip.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisScheduler {

    private final RedisService redisService;

    @Scheduled(cron = "0 0 4 * * *")
    public void updateSearchZSet() {
        redisService.reduceScores(RedisConstants.ZSET_SEARCH_RANKING_KEY, 0.95);
        redisService.reduceScores(RedisConstants.ZSET_CONTENT_RANKING_KEY, 0.6);
        redisService.reduceScores(RedisConstants.ZSET_PERSON_RANKING_KEY, 0.6);
        redisService.reduceScores(RedisConstants.ZSET_SPOT_RANKING_KEY, 0.6);
    }

}
