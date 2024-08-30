package site.dittotrip.ditto_trip.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.report.domain.dto.ReportListRes;
import site.dittotrip.ditto_trip.report.domain.dto.ReportSaveReq;
import site.dittotrip.ditto_trip.report.service.ReportService;
import site.dittotrip.ditto_trip.user.domain.User;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/list")
    @Operation(summary = "신고 리스트 조회 (관리자 기능)",
            description = "")
    public ReportListRes reportList(Pageable pageable) {
        return reportService.findReportList(pageable);
    }

    @PostMapping
    @Operation(summary = "신고 등록",
            description = "")
    public void reportSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @RequestBody ReportSaveReq reportSaveReq) {
        User user = userDetails.getUser();
        reportService.saveReport(user, reportSaveReq);
    }

    @PostMapping("/{reportId}")
    @Operation(summary = "신고 처리 (관리자 기능)",
            description = "")
    public void reportHandle() {

    }

}
