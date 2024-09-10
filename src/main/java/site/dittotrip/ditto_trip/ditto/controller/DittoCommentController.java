package site.dittotrip.ditto_trip.ditto.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoCommentSaveReq;
import site.dittotrip.ditto_trip.ditto.service.DittoCommentService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserFromUserDetails;

/**
 * 1. DittoComment 등록 (대댓글 포함)
 * 2. DittoComment 수정
 * 3. DittoComment 삭제
 */
@RestController
@RequestMapping("/ditto/{dittoId}/comment")
@RequiredArgsConstructor
public class DittoCommentController {

    private final DittoCommentService dittoCommentService;

    @PostMapping
    @Operation(summary = "디토 댓글 등록",
            description = "")
    public void dittoCommentSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable(name = "dittoId") Long dittoId,
                                 @RequestParam(name = "parentCommentId", required = false) Long parentCommentId,
                                 @RequestBody DittoCommentSaveReq saveReq) {
        User user = getUserFromUserDetails(userDetails, true);
        dittoCommentService.saveDittoComment(dittoId, parentCommentId, user, saveReq);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "디토 댓글 수정",
            description = "")
    public void dittoCommentModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "commentId") Long commentId,
                                   @RequestBody DittoCommentSaveReq saveReq) {
        User user = getUserFromUserDetails(userDetails, true);
        dittoCommentService.modifyDittoComment(commentId, user, saveReq);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "디토 댓글 삭제",
            description = "")
    public void dittoCommentRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "commentId") Long commentId) {
        User user = getUserFromUserDetails(userDetails, true);
        dittoCommentService.removeDittoComment(commentId, user);
    }

}
