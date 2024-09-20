package site.dittotrip.ditto_trip.exception.common;

import org.springframework.http.HttpStatus;

public class NoAuthorityException extends CustomException {

    private static final String MESSAGE = "해당 데이터에 대한 권한이 없습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }

}
