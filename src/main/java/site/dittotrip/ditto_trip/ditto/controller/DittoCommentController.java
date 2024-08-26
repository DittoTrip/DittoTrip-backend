package site.dittotrip.ditto_trip.ditto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoCommentSaveReq;
import site.dittotrip.ditto_trip.ditto.service.DittoCommentService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.service.CustomUserDetails.getUserFromUserDetails;

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
    public void dittoCommentSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable(name = "dittoId") Long dittoId,
                                 @RequestParam(name = "parentCommentId", required = false) Long parentCommentId,
                                 @RequestBody DittoCommentSaveReq saveReq) {
        User user = getUserFromUserDetails(userDetails);
        dittoCommentService.saveDittoComment(dittoId, parentCommentId, user, saveReq);
    }

    @PutMapping("/{commentId}")
    public void dittoCommentModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "commentId") Long commentId,
                                   @RequestBody DittoCommentSaveReq saveReq) {
        User user = getUserFromUserDetails(userDetails);
        dittoCommentService.modifyDittoComment(commentId, user, saveReq);
    }

    @DeleteMapping("/{commentId}")
    public void dittoCommentRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "commentId") Long commentId) {
        User user = getUserFromUserDetails(userDetails);
        dittoCommentService.removeDittoComment(commentId, user);
    }

}
