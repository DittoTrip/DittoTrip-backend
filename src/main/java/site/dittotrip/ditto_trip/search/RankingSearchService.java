package site.dittotrip.ditto_trip.search;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.search.dto.RankingSearchRes;
import site.dittotrip.ditto_trip.utils.RedisService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RankingSearchService {

    private final RedisService redisService;

    public RankingSearchRes findRankingSearchList() {
        List<String> rankingList = redisService.getRankingList();
        return RankingSearchRes.builder()
                .words(rankingList)
                .build();
    }

}
