package site.dittotrip.ditto_trip.spot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.exception.common.TooManyImagesException;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplyDetailRes;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplyListRes;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplyMiniListRes;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplySaveReq;
import site.dittotrip.ditto_trip.spot.service.SpotApplyService;

import java.util.List;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

@RestController
@RequestMapping("/spot/apply")
@RequiredArgsConstructor
public class SpotApplyController {

    private final SpotApplyService spotApplyService;

    @GetMapping("/list")
    @Operation(summary = "스팟 신청 검색 리스트 조회 (관리자 기능)",
            description = "query가 없는 경우 전체 조회")
    public SpotApplyListRes spotApplyList(@RequestParam(name = "query", required = false) String query,
                                          Pageable pageable) {
        return spotApplyService.findSpotApplyList(query, pageable);
    }

    @GetMapping("/list/my")
    @Operation(summary = "내 스팟 신청 리스트 조회",
            description = "")
    public SpotApplyMiniListRes mySpotApplyList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return spotApplyService.findMySpotApplyList(reqUserId);
    }

    @GetMapping("/{spotApplyId}")
    @Operation(summary = "스팟 신청 상세 조회",
            description = "등록한 유저와 관리자만 조회 가능합니다.")
    public SpotApplyDetailRes spotApplyDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @PathVariable(name = "spotApplyId") Long spotApplyId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return spotApplyService.findSpotApplyDetail(reqUserId, spotApplyId);
    }

    @PostMapping()
    @Operation(summary = "스팟 신청 등록",
            description = "image는 대표이미지입니다.\n" +
                    "images 제한 3개")
    public void spotApplySave(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @RequestPart(name = "saveReq") SpotApplySaveReq saveReq,
                              @RequestPart(name = "image") MultipartFile multipartFile,
                              @RequestPart(name = "images") List<MultipartFile> multipartFiles) {
        if (multipartFiles.size() > 3) {
            throw new TooManyImagesException();
        }

        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        spotApplyService.saveSpotApply(reqUserId, saveReq, multipartFile, multipartFiles);
    }

    @DeleteMapping("/{spotApplyId}")
    @Operation(summary = "스팟 신청 삭제 (관리자 기능)",
            description = "")
    public void spotApplyRemove(@PathVariable(name = "spotApplyId") Long spotApplyId) {
        spotApplyService.removeSpotApply(spotApplyId);
    }

    @PostMapping("/{spotApplyId}/handle")
    @Operation(summary = "스팟 신청 처리 (관리자 기능)",
            description = "")
    public void spotApplyHandle(@PathVariable(name = "spotApplyId") Long spotApplyId,
                                @RequestParam(name = "isApproval") Boolean isApproval) {
        spotApplyService.handleSpotApply(spotApplyId, isApproval);
    }

}
