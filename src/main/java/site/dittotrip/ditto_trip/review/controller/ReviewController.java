package site.dittotrip.ditto_trip.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.review.domain.dto.*;
import site.dittotrip.ditto_trip.exception.common.TooManyImagesException;
import site.dittotrip.ditto_trip.review.service.ReviewLikeService;
import site.dittotrip.ditto_trip.review.service.ReviewService;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserFromUserDetails;
import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

/**
 * 1. 리뷰 리스트 조회
 *  - 페이징
 * 2. 리뷰 상세 페이지
 * 3. 리뷰 작성
 * 4. 리뷰 수정
 * 5. 리뷰 삭제
 * 6. 리뷰 좋아요 등록
 * 7. 리뷰 좋아요 삭제
 */
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewLikeService reviewLikeService;

    @GetMapping("/spot/{spotId}/review/list")
    @Operation(summary = "스팟의 리뷰 리스트 조회",
            description = "")
    public ReviewListRes reviewList(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable(name = "spotId") Long spotId,
                                    Pageable pageable) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return reviewService.findReviewList(reqUserId, spotId, pageable);
    }

    /**
     * 작업 중
     */
    @GetMapping("/review/{reviewId}")
    @Operation(summary = "리뷰 상세 조회",
            description = "")
    public ReviewDetailRes reviewDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable(name = "reviewId") Long reviewId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, false);
        return reviewService.findReviewDetail(reqUserId, reviewId);
    }

    @PostMapping("/review")
    @Operation(summary = "리뷰 등록",
            description = "")
    public void reviewSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                           @RequestParam(name = "spotVisitId") Long spotVisitId,
                           @RequestPart(name = "saveReq") ReviewSaveReq saveReq,
                           @RequestPart(name = "images") List<MultipartFile> multipartFiles) {
        if (multipartFiles.size() > 10) {
            throw new TooManyImagesException();
        }

        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        reviewService.saveReview(reqUserId, spotVisitId, saveReq, multipartFiles);
    }

    @PutMapping("/review/{reviewId}")
    @Operation(summary = "리뷰 수정",
            description = "")
    public void reviewModify(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable(name = "reviewId") Long reviewId,
                             @RequestPart(name = "saveReq") ReviewSaveReq saveReq,
                             @RequestPart(name = "images") List<MultipartFile> multipartFiles) {
        if (multipartFiles.size() > 10) {
            throw new TooManyImagesException();
        }

        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        reviewService.modifyReview(reqUserId, reviewId, saveReq, multipartFiles);
    }

    @DeleteMapping("/review/{reviewId}")
    @Operation(summary = "리뷰 삭제",
            description = "")
    public void reviewRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable(name = "reviewId") Long reviewId) {

        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        reviewService.removeReview(reqUserId, reviewId);
    }

    /**
     * ReviewLike 기능
     */
    @GetMapping("/review/{reviewId}/like")
    @Operation(summary = "리뷰 좋아요 조회",
            description = "")
    public Boolean reviewLikeGet(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable(name = "reviewId") Long reviewId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        return reviewLikeService.getReviewLike(reqUserId, reviewId);
    }

    @PostMapping("/review/{reviewId}/like")
    @Operation(summary = "리뷰 좋아요 등록",
            description = "")
    public void reviewLikeSave(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable(name = "reviewId") Long reviewId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        reviewLikeService.saveReviewLike(reqUserId, reviewId);
    }

    @DeleteMapping("/review/{reviewId}/like")
    @Operation(summary = "리뷰 좋아요 삭제",
            description = "")
    public void reviewLikeRemove(@AuthenticationPrincipal CustomUserDetails userDetails,
                             @PathVariable(name = "reviewId") Long reviewId) {
        Long reqUserId = getUserIdFromUserDetails(userDetails, true);
        reviewLikeService.removeReviewLike(reqUserId, reviewId);
    }

}
