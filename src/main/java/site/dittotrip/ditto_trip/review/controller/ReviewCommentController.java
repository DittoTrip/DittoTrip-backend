package site.dittotrip.ditto_trip.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.review.domain.dto.ReviewCommentSaveReq;
import site.dittotrip.ditto_trip.review.service.ReviewCommentService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserFromUserDetails;

/**
 * 1. 댓글 등록 (대댓글 등록 포함)
 * 2. 댓글 수정
 * 3. 댓글 삭제
 */
@RestController
@RequestMapping("/review/{reviewId}/comment")
@RequiredArgsConstructor
public class ReviewCommentController {

    private final ReviewCommentService reviewCommentService;

    @PostMapping
    @Operation(summary = "리뷰 댓글 등록",
            description = "")
    public void commentSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "reviewId") Long reviewId,
                            @RequestParam(name = "parentCommentId", required = false) Long parentCommentId,
                            @RequestBody ReviewCommentSaveReq reviewCommentSaveReq) {
        User user = getUserFromUserDetails(userDetails, true);
        reviewCommentService.saveComment(reviewId, parentCommentId, user, reviewCommentSaveReq);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "리뷰 댓글 수정",
            description = "")
    public void commentModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable(name = "commentId") Long commentId,
                              @RequestBody ReviewCommentSaveReq reviewCommentSaveReq) {
        User user = getUserFromUserDetails(userDetails, true);
        reviewCommentService.modifyComment(commentId, user, reviewCommentSaveReq);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "리뷰 댓글 삭제",
            description = "")
    public void commentRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable(name = "commentId") Long commentId) {
        User user = getUserFromUserDetails(userDetails, true);
        reviewCommentService.removeComment(commentId, user);
    }

}
