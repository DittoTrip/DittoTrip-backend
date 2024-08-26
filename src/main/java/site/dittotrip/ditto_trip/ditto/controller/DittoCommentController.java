package site.dittotrip.ditto_trip.ditto.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.ditto.domain.dto.DittoCommentSaveReq;
import site.dittotrip.ditto_trip.ditto.service.DittoCommentService;
import site.dittotrip.ditto_trip.swagger.SwaggerAuth;
import site.dittotrip.ditto_trip.user.domain.User;

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
    @SwaggerAuth
    public void dittoCommentSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable(name = "dittoId") Long dittoId,
                                 @RequestParam(name = "parentCommentId", required = false) Long parentCommentId,
                                 @RequestBody DittoCommentSaveReq saveReq) {
        User user = userDetails.getUser();
        dittoCommentService.saveDittoComment(dittoId, parentCommentId, user, saveReq);
    }

    @PutMapping("/{commentId}")
    @SwaggerAuth
    public void dittoCommentModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "commentId") Long commentId,
                                   @RequestBody DittoCommentSaveReq saveReq) {
        User user = userDetails.getUser();
        dittoCommentService.modifyDittoComment(commentId, user, saveReq);
    }

    @DeleteMapping("/{commentId}")
    @SwaggerAuth
    public void dittoCommentRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable(name = "commentId") Long commentId) {
        User user = userDetails.getUser();
        dittoCommentService.removeDittoComment(commentId, user);
    }

}
