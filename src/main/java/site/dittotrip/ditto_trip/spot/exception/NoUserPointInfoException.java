package site.dittotrip.ditto_trip.spot.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class NoUserPointInfoException extends CustomException {

    private static final String MESSAGE = "유저의 위치 정보(userX, userY)가 필요합니다.";
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
