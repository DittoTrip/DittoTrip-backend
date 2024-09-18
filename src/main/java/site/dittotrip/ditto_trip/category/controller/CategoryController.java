package site.dittotrip.ditto_trip.category.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.category.domain.dto.*;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.category.service.CategoryBookmarkService;
import site.dittotrip.ditto_trip.category.service.CategoryService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserFromUserDetails;
import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

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
    public CategoryPageRes categoryPageList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestParam(name = "subType") CategorySubType subType,
                                            Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return categoryService.findCategoryList(reqUserId, subType, pageable);
    }

    @GetMapping("/list/bookmark")
    @Operation(summary = "내 북마크 카테고리 리스트 조회",
            description = "")
    public CategoryMajorTypeListRes categoryBookmarkList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                         @RequestParam(name = "majorType") CategoryMajorType majorType,
                                                         Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return categoryService.findCategoryListByBookmark(reqUserId, majorType, pageable);
    }

    @GetMapping("/list/search")
    @Operation(summary = "카테고리 리스트 검색 조회",
            description = "검색어(query)와 majorType에 의해 검색된 하나의 리스트를 반환합니다.")
    public CategoryMajorTypeListRes categorySearchList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestParam(name = "query") String query,
                                                       @RequestParam(name = "majorType") CategoryMajorType majorType,
                                                       Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return categoryService.findCategoryListBySearch(reqUserId, query, majorType, pageable);
    }

    @GetMapping("/list/search/typeless")
    @Operation(summary = "카테고리 리스트 검색 조회 (관리자도 사용)",
            description = "타입에 상관없이 하나의 리스트를 반환합니다.")
    public CategoryListNoTypeRes categorySearchNoTypeList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                          @RequestParam(name = "query", required = false) String query,
                                                          Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return categoryService.findCategoryNoTypeListBySearch(reqUserId, query, pageable);
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
            description = "관리자 권한이 없는 유저의 요청은 인증 필터에 의해 거부됩니다.\n" +
                    "이미지는 필수가 아닙니다.")
    public void categoryModify(@PathVariable(name = "categoryId") Long categoryId,
                               @RequestBody CategoryModifyReq categoryModifyReq,
                               @RequestParam(name = "image", required = false) MultipartFile multipartFile) {
        categoryService.modifyCategory(categoryId, categoryModifyReq, multipartFile);
    }

    @DeleteMapping("{categoryId}")
    @Operation(summary = "카테고리 삭제 (관리자 기능)",
            description = "관리자 권한이 없는 유저의 요청은 인증 필터에 의해 거부됩니다.")
    public void categoryRemove(@PathVariable(name = "categoryId") Long categoryId) {
        categoryService.removeCategory(categoryId);
    }

    @GetMapping("/{categoryId}/bookmark")
    @Operation(summary = "카테고리 북마크 조회",
            description = "Boolean 데이터를 반환")
    public Boolean CategoryBookmarkGet(@PathVariable(name = "categoryId") Long categoryId,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return categoryBookmarkService.findCategoryBookmark(reqUserId, categoryId);
    }

    @PostMapping("/{categoryId}/bookmark")
    @Operation(summary = "카테고리 북마크 추가",
            description = "")
    public void CategoryBookmarkAdd(@PathVariable(name = "categoryId") Long categoryId,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        categoryBookmarkService.addCategoryBookmark(reqUserId, categoryId);
    }

    @DeleteMapping("/{categoryId}/bookmark")
    @Operation(summary = "카테고리 북마크 삭제",
            description = "")
    public void CategoryBookmarkRemove(@PathVariable(name = "categoryId") Long categoryId,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        categoryBookmarkService.removeCategoryBookmark(reqUserId, categoryId);
    }

}
