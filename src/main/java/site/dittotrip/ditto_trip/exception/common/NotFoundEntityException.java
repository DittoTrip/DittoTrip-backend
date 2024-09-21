package site.dittotrip.ditto_trip.exception.common;

import org.springframework.http.HttpStatus;

public class NotFoundEntityException extends CustomException {

    private static final String MESSAGE = "해당 데이터를 찾지 못했습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }

}