package site.dittotrip.ditto_trip.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.review.service.ReviewLikeService;

/**
 * ReviewController 합치기
 */
@RestController
@RequestMapping("/review/{reviewId}/like")
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikeService reviewLikeService;

}
