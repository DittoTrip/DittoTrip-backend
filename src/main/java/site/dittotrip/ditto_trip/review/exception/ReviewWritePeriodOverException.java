package site.dittotrip.ditto_trip.review.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class ReviewWritePeriodOverException extends CustomException {

    private static final String MESSAGE = "해당 스팟방문에 대해 작성 가능한 기간이 지났습니다.";
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
