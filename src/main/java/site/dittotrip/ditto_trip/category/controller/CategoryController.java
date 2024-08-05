package site.dittotrip.ditto_trip.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes;
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

}
