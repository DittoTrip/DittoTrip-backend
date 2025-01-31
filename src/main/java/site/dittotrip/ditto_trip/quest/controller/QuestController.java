package site.dittotrip.ditto_trip.quest.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.quest.domain.dto.QuestListRes;
import site.dittotrip.ditto_trip.quest.domain.dto.QuestSaveReq;
import site.dittotrip.ditto_trip.quest.domain.dto.UserQuestListRes;
import site.dittotrip.ditto_trip.quest.domain.enums.UserQuestStatus;
import site.dittotrip.ditto_trip.quest.service.QuestService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserFromUserDetails;
import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

/**
 * 1. Quest 리스트 조회
 * 2. Quest 달성하기 (보상 획득하기)
 */
@RestController
@RequestMapping("/quest")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;

    @GetMapping("/list")
    @Operation(summary = "퀘스트 리스트 조회",
            description = "")
    public UserQuestListRes questList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestParam(name = "UserQuestStatus") UserQuestStatus userQuestStatus,
                                      Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return questService.findQuestList(reqUserId, userQuestStatus, pageable);
    }

    @PostMapping("/{userQuestId}/achieve")
    @Operation(summary = "퀘스트 달성하고 보상 받기",
            description = "")
    public void questAchieve(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable(name = "userQuestId") Long userQuestId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        questService.achieveQuest(reqUserId, userQuestId);
    }

    @PostMapping
    @Operation(summary = "퀘스트 등록 (관리자 기능)",
            description = "")
    public void questSave(@RequestBody QuestSaveReq saveReq) {
        questService.saveQuest(saveReq);
    }

    @GetMapping("/list/admin")
    @Operation(summary = "전체 퀘스트 리스트 조회 (관리자 기능)",
            description = "")
    public QuestListRes questListForAdmin() {
        return questService.findQuestListForAdmin();
    }

}
