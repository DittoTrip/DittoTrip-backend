package site.dittotrip.ditto_trip.spot.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class AlreadyHandledSpotApplyException extends CustomException {

    private static final String MESSAGE = "이미 처리된 스팟 신청입니다..";
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
