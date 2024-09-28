package site.dittotrip.ditto_trip.searchpage.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SearchPageRes {

    private List<String> words;

    private List<CategorySearchPageData> categorySearchPageDataList = new ArrayList<>();

}
