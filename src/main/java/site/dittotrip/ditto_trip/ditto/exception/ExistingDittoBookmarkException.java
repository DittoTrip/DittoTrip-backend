package site.dittotrip.ditto_trip.ditto.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class ExistingDittoBookmarkException extends CustomException {

    private static final String MESSAGE = "이미 등록된 디토 북마크입니다.";
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
