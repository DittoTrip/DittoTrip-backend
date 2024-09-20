package site.dittotrip.ditto_trip.category.exception;

import org.springframework.http.HttpStatus;
import site.dittotrip.ditto_trip.exception.common.CustomException;

public class ExistingCategoryBookmarkException extends CustomException {

    private static final String MESSAGE = "이미 등록된 카테고리 북마크입니다.";
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
