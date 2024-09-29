package site.dittotrip.ditto_trip.searchpage;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.searchpage.dto.RankingCategoryRes;
import site.dittotrip.ditto_trip.searchpage.dto.RankingSearchRes;

@RestController
@RequestMapping("/search-page/ranking")
@RequiredArgsConstructor
public class SearchPageController {

    private final SearchPageService searchPageService;

    @GetMapping("/words")
    @Operation(summary = "인기 검색어 리스트 조회",
            description = "")
    public RankingSearchRes rankingSearch() {
        return searchPageService.findRankingSearchList();
    }

    @GetMapping("/category")
    @Operation(summary = "인기 카테고리 리스트 조회",
            description = "")
    public RankingCategoryRes rankingCategory(@RequestParam(name = "majorType")CategoryMajorType majorType) {
        return searchPageService.findRankingCategoryList(majorType);
    }

}
