package site.dittotrip.ditto_trip.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import site.dittotrip.ditto_trip.auth.service.CustomUserDetails;
import site.dittotrip.ditto_trip.review.domain.dto.CommentSaveReq;
import site.dittotrip.ditto_trip.review.service.CommentService;
import site.dittotrip.ditto_trip.user.domain.User;

import static site.dittotrip.ditto_trip.auth.service.CustomUserDetails.getUserFromUserDetails;

/**
 * 1. 댓글 등록 (대댓글 등록 포함)
 * 2. 댓글 수정
 * 3. 댓글 삭제
 */
@RestController
@RequestMapping("/review/{reviewId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void commentSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "reviewId") Long reviewId,
                            @RequestParam(name = "parentCommentId", required = false) Long parentCommentId,
                            @RequestBody CommentSaveReq commentSaveReq) {
        User user = getUserFromUserDetails(userDetails);
        commentService.saveComment(reviewId, parentCommentId, user, commentSaveReq);
    }

    @PutMapping("/{commentId}")
    public void commentModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable(name = "commentId") Long commentId,
                              @RequestBody CommentSaveReq commentSaveReq) {
        User user = getUserFromUserDetails(userDetails);
        commentService.modifyComment(commentId, user, commentSaveReq);
    }

    @DeleteMapping("/{commentId}")
    public void commentRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @PathVariable(name = "commentId") Long commentId) {
        User user = getUserFromUserDetails(userDetails);
        commentService.removeComment(commentId, user);
    }

}
