package site.dittotrip.ditto_trip.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.dittotrip.ditto_trip.exception.common.NoAuthorityException;
import site.dittotrip.ditto_trip.review.exception.AlreadyWriteReviewException;
import site.dittotrip.ditto_trip.review.exception.DoubleChildReviewCommentException;
import site.dittotrip.ditto_trip.review.exception.ExistingReviewLikeException;
import site.dittotrip.ditto_trip.review.exception.ReviewWritePeriodOverException;
import site.dittotrip.ditto_trip.reward.exception.NotMatchedItemTypeException;

import java.util.Objects;

@RestController
@RequestMapping("/test/error")
public class ErrorController {

    @GetMapping
    @Operation(summary = "에러 API 테스트",
            description = "parameter\n" +
                    "1. internal\n" +
                    "2. al\n" +
                    "3. do\n" +
                    "4. ex\n" +
                    "5. re\n" +
                    "6. no\n" +
                    "7. No\n")
    public String test(@RequestParam(name = "ex") String ex) {
        if (Objects.equals(ex, "internal")) {
            throw new IllegalArgumentException("test");
        }

        if (Objects.equals(ex, "al")) {
            throw new AlreadyWriteReviewException();
        }

        if (Objects.equals(ex, "do")) {
            throw new DoubleChildReviewCommentException();
        }

        if (Objects.equals(ex, "ex")) {
            throw new ExistingReviewLikeException();
        }

        if (Objects.equals(ex, "re")) {
            throw new ReviewWritePeriodOverException();
        }

        if (Objects.equals(ex, "no")) {
            throw new NotMatchedItemTypeException();
        }

        if (Objects.equals(ex, "No")) {
            throw new NoAuthorityException();
        }

        return "ok";
    }


}
