package site.dittotrip.ditto_trip.spot.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class SpotVisitDistanceException extends CustomException {

    private static final String MESSAGE = "유저와 스팟 간의 거리가 너무 멉니다.";
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
