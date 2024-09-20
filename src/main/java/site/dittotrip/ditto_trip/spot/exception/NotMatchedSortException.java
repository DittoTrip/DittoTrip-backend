package site.dittotrip.ditto_trip.spot.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class NotMatchedSortException extends CustomException {

    private static final String MESSAGE = "Sort 파라미터가 적절하지 않습니다. 가능한 sort 파라미터는 아래와 같습니다.\n" +
            "1. createdDateTime: DESC\n" +
            "2. rating: DESC\n" +
            "3. distance: ASC";
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
