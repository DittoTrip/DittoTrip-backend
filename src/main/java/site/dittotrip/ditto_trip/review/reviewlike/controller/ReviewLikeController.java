package site.dittotrip.ditto_trip.review.reviewlike.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.review.reviewlike.service.ReviewLikeRemoveService;
import site.dittotrip.ditto_trip.review.reviewlike.service.ReviewLikeSaveService;

@RestController
@RequestMapping("/review/{reviewId}/like")
@RequiredArgsConstructor
public class ReviewLikeController {

    private final ReviewLikeSaveService reviewLikeSaveService;
    private final ReviewLikeRemoveService reviewLikeRemoveService;

}
