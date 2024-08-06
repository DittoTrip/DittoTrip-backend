package site.dittotrip.ditto_trip.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.category.domain.dto.detail.CategoryDetailRes;
import site.dittotrip.ditto_trip.category.domain.dto.list.CategoryListRes;
import site.dittotrip.ditto_trip.category.service.CategoryDetailService;
import site.dittotrip.ditto_trip.category.service.CategoryListService;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryListService categoryListService;
    private final CategoryDetailService categoryDetailService;

    @GetMapping("/list")
    public CategoryListRes categoryList() {
        return categoryListService.findCategoryList();
    }

    @GetMapping("/list/search")
    public CategoryListRes categorySearchList(@RequestParam(name = "query") String query) {
        return categoryListService.findCategoryListBySearch(query);
    }

    @GetMapping("/{categoryId}")
    public CategoryDetailRes categoryDetail(@PathVariable(name = "categoryId") Long categoryId) {
        return categoryDetailService.findCategoryDetail(categoryId);
    }

}
