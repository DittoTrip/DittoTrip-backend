package site.dittotrip.ditto_trip.quest.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class TooManyNowCountException extends CustomException {

    private static final String MESSAGE = "nowCount 가 conditionCount 를 넘어갔습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }

}
