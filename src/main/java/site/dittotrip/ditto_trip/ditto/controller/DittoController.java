package site.dittotrip.ditto_trip.ditto.controller;

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
import site.dittotrip.ditto_trip.swagger.SwaggerAuth;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

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
    @SwaggerAuth
    public DittoListRes dittoList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  Pageable pageable) {
        User user = userDetails.getUser();
        return dittoService.findDittoList(user, pageable);
    }

    @GetMapping("/list/bookmark")
    @SwaggerAuth
    public DittoListRes dittoListInBookmark(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return dittoService.findDittoListInBookmark(user);
    }

    @GetMapping("/{dittoId}")
    @SwaggerAuth
    public DittoDetailRes dittoDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @PathVariable(name = "dittoId") Long dittoId) {
        User user = userDetails.getUser();
        return dittoService.findDittoDetail(dittoId, user);
    }

    @PostMapping
    @SwaggerAuth
    public void dittoSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                          @RequestBody DittoSaveReq saveReq,
                          @RequestParam(name = "images") List<MultipartFile> multipartFiles) {
        User user = userDetails.getUser();
        dittoService.saveDitto(user, saveReq, multipartFiles);
    }

    @PutMapping("/{dittoId}")
    @SwaggerAuth
    public void dittoModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "dittoId") Long dittoId,
                            @RequestBody DittoModifyReq modifyReq,
                            @RequestParam(name = "images") List<MultipartFile> multipartFiles) {
        User user = userDetails.getUser();
        dittoService.modifyDitto(dittoId, user, modifyReq, multipartFiles);
    }

    @DeleteMapping("/{dittoId}")
    @SwaggerAuth
    public void dittoRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "dittoId") Long dittoId) {
        User user = userDetails.getUser();
        dittoService.removeDitto(dittoId, user);
    }

    /**
     * DittoBookmark 기능
     */
    @PostMapping("/bookmark/{dittoId}")
    @SwaggerAuth
    public void dittoBookmarkSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  @PathVariable(name = "dittoId") Long dittoId) {
        User user = userDetails.getUser();
        dittoBookmarkService.saveDittoBookmark(dittoId, user);
    }

    @DeleteMapping("/bookmark/{dittoId}")
    @SwaggerAuth
    public void dittoBookmarkRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable(name = "dittoId") Long dittoId) {
        User user = userDetails.getUser();
        dittoBookmarkService.removeDittoBookmark(dittoId, user);
    }

}
