package site.dittotrip.ditto_trip.review.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class AlreadyWriteReviewException extends CustomException {

    private static final String MESSAGE = "해당 스팟방문에 대해 이미 작성한 리뷰가 있습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }

}
