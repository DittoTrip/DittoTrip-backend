package site.dittotrip.ditto_trip.alarm.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.alarm.domain.dto.AlarmListRes;
import site.dittotrip.ditto_trip.alarm.domain.dto.AlarmSaveReq;
import site.dittotrip.ditto_trip.alarm.service.AlarmService;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

/**
 * 1. 알림 리스트 조회
 */
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/list")
    @Operation(summary = "알림 리스트 조회",
            description = "")
    public AlarmListRes alarmList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return alarmService.findAlarmList(reqUserId, pageable);
    }

    @PostMapping
    @Operation(summary = "알림 등록 (관리자 기능)",
            description = "")
    public void alarmSave(@RequestBody AlarmSaveReq saveReq) {
        alarmService.saveAlarms(saveReq);
    }

}
