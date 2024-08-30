package site.dittotrip.ditto_trip.spot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplySaveReq;
import site.dittotrip.ditto_trip.spot.service.SpotApplyService;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

@RestController
@RequestMapping("/spot/apply")
@RequiredArgsConstructor
public class SpotApplyController {

    private final SpotApplyService spotApplyService;

    @GetMapping("/spot/apply/list")
    @Operation(summary = "내 스팟 신청 리스트 조회 (디자인 나오고 작업 예정)",
            description = "")
    public void spotApplyList(@AuthenticationPrincipal CustomUserDetails userDetails) {

    }

    @PostMapping("/spot/apply")
    @Operation(summary = "스팟 신청 등록",
            description = "image는 대표이미지입니다.")
    public void spotApplySave(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @RequestBody SpotApplySaveReq saveReq,
                              @RequestParam(name = "image") MultipartFile multipartFile,
                              @RequestParam(name = "images") List<MultipartFile> multipartFiles) {
        User user = userDetails.getUser();
        spotApplyService.saveSpotApply(user, saveReq, multipartFile, multipartFiles);
    }

    @DeleteMapping("/spot/apply/{spotApplyId}")
    @Operation(summary = "스팟 신청 삭제 (관리자 기능)",
            description = "")
    public void spotApplyRemove(@PathVariable(name = "spotApplyId") Long spotApplyId) {
        spotApplyService.removeSpotApply(spotApplyId);
    }

}
