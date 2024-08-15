package site.dittotrip.ditto_trip.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.category.domain.dto.detail.CategoryDetailRes;
import site.dittotrip.ditto_trip.category.domain.dto.list.CategoryListRes;
import site.dittotrip.ditto_trip.category.service.CategoryService;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public CategoryListRes categoryList() {
        return categoryService.findCategoryList();
    }

    @GetMapping("/list/search")
    public CategoryListRes categorySearchList(@RequestParam(name = "query") String query) {
        return categoryService.findCategoryListBySearch(query);
    }

    @GetMapping("/{categoryId}")
    public CategoryDetailRes categoryDetail(@PathVariable(name = "categoryId") Long categoryId) {
        return categoryService.findCategoryDetail(categoryId);
    }

}
