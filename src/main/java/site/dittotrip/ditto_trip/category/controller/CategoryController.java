package site.dittotrip.ditto_trip.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes;
import site.dittotrip.ditto_trip.category.service.CategoryListService;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryListService categoryListService;

    @GetMapping("/list")
    public CategoryListRes categoryList() {
        return categoryListService.findCategoryList();
    }

    @GetMapping("/list/search")
    public CategoryListRes categorySearchList(@RequestParam(name = "query") String query) {
        return categoryListService.findCategoryListBySearch(query);
    }

    /**
     * 미구현
     */
    @GetMapping("/{categoryId}")
    public void categoryDetail(@PathVariable(name = "categoryId") Long categoryId) {

    }

}
