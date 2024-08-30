package site.dittotrip.ditto_trip.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryListRes;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryModifyReq;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryPageRes;
import site.dittotrip.ditto_trip.category.domain.dto.CategorySaveReq;
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
    public CategoryPageRes categoryPageList(@RequestParam(name = "subType") CategorySubType subType,
                                            Pageable pageable) {
        return categoryService.findCategoryList(subType, pageable);
    }

    @GetMapping("/list/bookmark")
    @Operation(summary = "내 북마크 카테고리 리스트 조회",
            description = "")
    public CategoryListRes categoryBookmarkList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        return categoryService.findCategoryListByBookmark(user);
    }

    @GetMapping("/list/search")
    @Operation(summary = "카테고리 리스트 검색 조회",
            description = "")
    public CategoryListRes categorySearchList(@RequestParam(name = "query") String query) {
        return categoryService.findCategoryListBySearch(query);
    }

    @PostMapping
    @Operation(summary = "카테고리 저장 (관리자 기능)",
            description = "관리자 권한이 없는 유저의 요청은 인증 필터에 의해 거부됩니다.")
    public void categorySave(@RequestBody CategorySaveReq categorySaveReq,
                             @RequestParam(name = "image") MultipartFile multipartFile) {
        categoryService.saveCategory(categorySaveReq, multipartFile);
    }

    @PutMapping("/{categoryId}")
    @Operation(summary = "카테고리 수정 (관리자 기능)",
            description = "이미지는 필수가 아닙니다.")
    public void categoryModify(@PathVariable(name = "categoryId") Long categoryId,
                               @RequestBody CategoryModifyReq categoryModifyReq,
                               @RequestParam(name = "image", required = false) MultipartFile multipartFile) {
        categoryService.modifyCategory(categoryId, categoryModifyReq, multipartFile);
    }

    @PostMapping("/{categoryId}/bookmark")
    @Operation(summary = "카테고리 북마크 추가",
            description = "")
    public void CategoryBookmarkAdd(@PathVariable(name = "categoryId") Long categoryId,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        categoryBookmarkService.addCategoryBookmark(categoryId, user);
    }

    @DeleteMapping("/{categoryId}/bookmark")
    @Operation(summary = "카테고리 북마크 삭제",
            description = "")
    public void CategoryBookmarkRemove(@PathVariable(name = "categoryId") Long categoryId,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        categoryBookmarkService.removeCategoryBookmark(categoryId, user);
    }

}
