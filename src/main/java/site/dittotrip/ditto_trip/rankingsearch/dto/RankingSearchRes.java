package site.dittotrip.ditto_trip.rankingsearch.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RankingSearchRes {

    private List<String> words;

}
