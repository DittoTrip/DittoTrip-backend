package site.dittotrip.ditto_trip.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryDetailRes;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes;
import site.dittotrip.ditto_trip.category.service.CategoryBookmarkService;
import site.dittotrip.ditto_trip.category.service.CategoryService;
import site.dittotrip.ditto_trip.user.domain.User;

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
    public void CategoryBookmarkAdd(@PathVariable(name = "categoryId") Long categoryId,
                                    @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        categoryBookmarkService.addCategoryBookmark(categoryId, user);
    }

    @DeleteMapping("/{categoryId}")
    public void CategoryBookmarkRemove(@PathVariable(name = "categoryId") Long categoryId,
                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        categoryBookmarkService.removeCategoryBookmark(categoryId, user);
    }

}
