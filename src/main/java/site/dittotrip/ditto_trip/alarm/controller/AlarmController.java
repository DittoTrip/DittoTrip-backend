package site.dittotrip.ditto_trip.alarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.alarm.domain.dto.AlarmListRes;
import site.dittotrip.ditto_trip.alarm.service.AlarmService;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.service.CustomUserDetails.*;

/**
 * 1. 알림 리스트 조회
 */
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/list")
    public AlarmListRes alarmList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                  Pageable pageable) {
        User user = getUserFromUserDetails(userDetails, true);
        return alarmService.findAlarmList(user, pageable);
    }

}
