package site.dittotrip.ditto_trip.searchpage;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.searchpage.dto.SearchPageRes;

@RestController
@RequestMapping("/ranking-search")
@RequiredArgsConstructor
public class SearchPageController {

    private final SearchPageService searchPageService;

    @GetMapping
    @Operation(summary = "검색 페이지 조회",
            description = "")
    public SearchPageRes searchPage() {
        return searchPageService.findSearchPage();
    }

}
