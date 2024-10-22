package site.dittotrip.ditto_trip.ditto.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoDetailRes;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoListRes;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoSaveReq;
import site.dittotrip.ditto_trip.ditto.service.DittoBookmarkService;
import site.dittotrip.ditto_trip.ditto.service.DittoService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserFromUserDetails;
import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

/**
 * 1. Ditto 랜덤 리스트 조회
 *  -> 기획 필요
 * 2. 북마크 Ditto 리스트 조회
 * 3. Ditto 상세 조회 -> 댓글 조회 포함시키기
 * 4. Ditto 등록
 * 5. Ditto 수정
 * 6. Ditto 삭제
 * 7. Ditto 북마크 등록
 * 8. Ditto 북마크 삭제
 */
@RestController
@RequestMapping("/ditto")
@RequiredArgsConstructor
public class DittoController {

    private final DittoService dittoService;
    private final DittoBookmarkService dittoBookmarkService;

    @GetMapping("/list")
    @Operation(summary = "디토 리스트 조회 (디토 페이지)",
            description = "")
    public DittoListRes dittoList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return dittoService.findDittoList(reqUserId, pageable);
    }

    @GetMapping("/list/bookmark")
    @Operation(summary = "내 북마크 디토 리스트 조회",
            description = "")
    public DittoListRes dittoListInBookmark(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return dittoService.findDittoListInBookmark(reqUserId);
    }

    @GetMapping("/list/search")
    @Operation(summary = "디토 리스트 검색 조회",
            description = "")
    public DittoListRes dittoSearchList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestParam(name = "query") String query,
                                        Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return dittoService.findDittoListBySearch(reqUserId, query, pageable);
    }
    @GetMapping("/list/user/{userId}")
    @Operation(summary = "유저의 디토 리스트 조회",
            description = "")
    public DittoListRes dittoListOfUser(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable(name = "userId") Long userId,
                                        Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return dittoService.findUsersDittoList(reqUserId, userId, pageable);
    }

    @GetMapping("/{dittoId}")
    @Operation(summary = "디토 상세 조회",
            description = "")
    public DittoDetailRes dittoDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable(name = "dittoId") Long dittoId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return dittoService.findDittoDetail(reqUserId, dittoId);
    }

    @PostMapping
    @Operation(summary = "디토 등록",
            description = "")
    public void dittoSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @RequestPart(name = "saveReq") DittoSaveReq saveReq,
                          @RequestPart(name = "image") MultipartFile multipartFile) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        dittoService.saveDitto(reqUserId, saveReq, multipartFile);
    }

    @PutMapping("/{dittoId}")
    @Operation(summary = "디토 수정",
            description = "")
    public void dittoModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "dittoId") Long dittoId,
                            @RequestPart(name = "saveReq") DittoSaveReq saveReq,
                            @RequestPart(name = "image") MultipartFile multipartFile) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        dittoService.modifyDitto(reqUserId, dittoId, saveReq, multipartFile);
    }

    @DeleteMapping("/{dittoId}")
    @Operation(summary = "디토 삭제",
            description = "")
    public void dittoRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "dittoId") Long dittoId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        dittoService.removeDitto(reqUserId, dittoId);
    }

    /**
     * DittoBookmark 기능
     */
    @GetMapping("/{dittoId}/bookmark")
    @Operation(summary = "디토 북마크 조회",
            description = "")
    public Boolean dittoBookmarkGet(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable(name = "dittoId") Long dittoId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return dittoBookmarkService.getDittoBookmark(reqUserId, dittoId);
    }

    @PostMapping("/{dittoId}/bookmark")
    @Operation(summary = "디토 북마크 추가",
            description = "")
    public void dittoBookmarkSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable(name = "dittoId") Long dittoId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        dittoBookmarkService.saveDittoBookmark(reqUserId, dittoId);
    }

    @DeleteMapping("/{dittoId}/bookmark")
    @Operation(summary = "디토 북마크 삭제",
            description = "")
    public void dittoBookmarkRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable(name = "dittoId") Long dittoId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        dittoBookmarkService.removeDittoBookmark(reqUserId, dittoId);
    }

}
