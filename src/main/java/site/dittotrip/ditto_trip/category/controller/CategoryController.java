package site.dittotrip.ditto_trip.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryPageRes;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.category.service.CategoryBookmarkService;
import site.dittotrip.ditto_trip.category.service.CategoryService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.service.CustomUserDetails.getUserFromUserDetails;

/**
 * 1. 카테고리 탐색 첫 페이지 (majorType 별 리스트 -> PERSON / CONTENT)
 *      => subType 별 리스트들 반환 (리스트들)
 * 2. 즐겨찾는 카테고리
 * 3. 검색 조회
 * !! pagination 전부 10개 단위
 *  카테고리 subType 추가 페이지네이션
 *      => subType 리스트 하나 반환
 */
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryBookmarkService categoryBookmarkService;

    @GetMapping("/list")
    @Operation(summary = "카테고리 리스트 조회",
            description = "")
    public CategoryListRes categoryList(@RequestParam(name = "majorType") CategoryMajorType majorType) {
        return categoryService.findCategoryList(majorType);
    }

    @GetMapping("/list/bookmark")
    @Operation(summary = "카테고리 리스트 조회",
            description = "")
    public CategoryListRes categoryBookmarkList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        return categoryService.findCategoryListByBookmark(user);
    }

    @GetMapping("/list/search")
    @Operation(summary = "카테고리 리스트 조회",
            description = "")
    public CategoryListRes categorySearchList(@RequestParam(name = "query") String query) {
        return categoryService.findCategoryListBySearch(query);
    }

    @GetMapping("/list/add")
    @Operation(summary = "카테고리 리스트 조회",
            description = "")
    public CategoryPageRes categoryPageList(@RequestParam(name = "subType") CategorySubType subType,
                                            @RequestParam(name = "page") Integer page) {
        return categoryService.findCategoryPage(subType, page);
    }

    @PostMapping("/{categoryId}/bookmark")
    @Operation(summary = "카테고리 리스트 조회",
            description = "")
    public void CategoryBookmarkAdd(@PathVariable(name = "categoryId") Long categoryId,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        categoryBookmarkService.addCategoryBookmark(categoryId, user);
    }

    @DeleteMapping("/{categoryId}/bookmark")
    @Operation(summary = "카테고리 리스트 조회",
            description = "")
    public void CategoryBookmarkRemove(@PathVariable(name = "categoryId") Long categoryId,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        categoryBookmarkService.removeCategoryBookmark(categoryId, user);
    }

}
