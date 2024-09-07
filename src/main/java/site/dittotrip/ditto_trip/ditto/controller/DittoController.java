package site.dittotrip.ditto_trip.ditto.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoDetailRes;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoListRes;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoModifyReq;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoSaveReq;
import site.dittotrip.ditto_trip.ditto.service.DittoBookmarkService;
import site.dittotrip.ditto_trip.ditto.service.DittoService;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

import static site.dittotrip.ditto_trip.auth.service.CustomUserDetails.getUserFromUserDetails;

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
        User user = getUserFromUserDetails(userDetails, false);
        return dittoService.findDittoList(user, pageable);
    }

    @GetMapping("/list/bookmark")
    @Operation(summary = "내 북마크 디토 리스트 조회",
            description = "")
    public DittoListRes dittoListInBookmark(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = getUserFromUserDetails(userDetails, true);
        return dittoService.findDittoListInBookmark(user);
    }

    @GetMapping("/{dittoId}")
    @Operation(summary = "디토 상세 조회",
            description = "")
    public DittoDetailRes dittoDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable(name = "dittoId") Long dittoId) {
        User user = getUserFromUserDetails(userDetails, false);
        return dittoService.findDittoDetail(dittoId, user);
    }

    @PostMapping
    @Operation(summary = "디토 등록",
            description = "")
    public void dittoSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @RequestBody DittoSaveReq saveReq,
                          @RequestParam(name = "image") MultipartFile multipartFile) {
        User user = getUserFromUserDetails(userDetails, true);
        dittoService.saveDitto(user, saveReq, multipartFile);
    }

    @PutMapping("/{dittoId}")
    @Operation(summary = "디토 수정",
            description = "")
    public void dittoModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "dittoId") Long dittoId,
                            @RequestBody DittoSaveReq saveReq,
                            @RequestParam(name = "image") MultipartFile multipartFile) {
        User user = getUserFromUserDetails(userDetails, true);
        dittoService.modifyDitto(dittoId, user, saveReq, multipartFile);
    }

    @DeleteMapping("/{dittoId}")
    @Operation(summary = "디토 삭제",
            description = "")
    public void dittoRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "dittoId") Long dittoId) {
        User user = getUserFromUserDetails(userDetails, true);
        dittoService.removeDitto(dittoId, user);
    }

    /**
     * DittoBookmark 기능
     */
    @GetMapping("/{dittoId}/bookmark")
    @Operation(summary = "디토 북마크 조회",
            description = "")
    public Boolean dittoBookmarkGet(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable(name = "dittoId") Long dittoId) {
        User user = getUserFromUserDetails(userDetails, true);
        return dittoBookmarkService.getDittoBookmark(dittoId, user);
    }

    @PostMapping("/{dittoId}/bookmark")
    @Operation(summary = "디토 북마크 추가",
            description = "")
    public void dittoBookmarkSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable(name = "dittoId") Long dittoId) {
        User user = getUserFromUserDetails(userDetails, true);
        dittoBookmarkService.saveDittoBookmark(dittoId, user);
    }

    @DeleteMapping("/{dittoId}/bookmark")
    @Operation(summary = "디토 북마크 삭제",
            description = "")
    public void dittoBookmarkRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable(name = "dittoId") Long dittoId) {
        User user = getUserFromUserDetails(userDetails, true);
        dittoBookmarkService.removeDittoBookmark(dittoId, user);
    }

}
