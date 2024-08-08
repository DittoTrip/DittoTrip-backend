package site.dittotrip.ditto_trip.review.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.review.comment.service.CommentListService;
import site.dittotrip.ditto_trip.review.comment.service.CommentModifyService;
import site.dittotrip.ditto_trip.review.comment.service.CommentRemoveService;
import site.dittotrip.ditto_trip.review.comment.service.CommentSaveService;

@RestController
@RequestMapping("/review/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentListService commentListService;
    private final CommentSaveService commentSaveService;
    private final CommentModifyService commentModifyService;
    private final CommentRemoveService commentRemoveService;

}
