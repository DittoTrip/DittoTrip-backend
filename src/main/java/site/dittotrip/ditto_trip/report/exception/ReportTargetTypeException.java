package site.dittotrip.ditto_trip.report.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class ReportTargetTypeException extends CustomException {

    private static final String MESSAGE = "Target Entity class 와 TargetId 가 매칭되지 않습니다.";
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
