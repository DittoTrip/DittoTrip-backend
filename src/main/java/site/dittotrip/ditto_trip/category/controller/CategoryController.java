package site.dittotrip.ditto_trip.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryDetailRes;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes;
import site.dittotrip.ditto_trip.category.service.CategoryBookmarkService;
import site.dittotrip.ditto_trip.category.service.CategoryService;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryBookmarkService categoryBookmarkService;

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

    @PostMapping("/{categoryId}")
    public void CategoryBookmarkAdd(@PathVariable(name = "categoryId") Long categoryId) {

    }

    @DeleteMapping("/{categoryId]")
    public void CategoryBookmarkRemove(@PathVariable(name = "categoryId") Long categoryId) {

    }

}
