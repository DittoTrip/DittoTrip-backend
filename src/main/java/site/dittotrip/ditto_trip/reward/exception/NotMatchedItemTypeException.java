package site.dittotrip.ditto_trip.reward.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class NotMatchedItemTypeException extends CustomException {

    private static final String MESSAGE = "Item 과 ItemType 이 매치되지 않습니다.";
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
