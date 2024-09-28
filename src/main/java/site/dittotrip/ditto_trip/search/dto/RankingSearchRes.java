package site.dittotrip.ditto_trip.search.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RankingSearchRes {

    private List<String> words;

}
