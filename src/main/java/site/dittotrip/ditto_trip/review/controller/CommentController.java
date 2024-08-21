package site.dittotrip.ditto_trip.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.review.service.CommentService;

@RestController
@RequestMapping("/review/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

}
