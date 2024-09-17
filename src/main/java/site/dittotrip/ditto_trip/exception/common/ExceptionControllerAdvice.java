package site.dittotrip.ditto_trip.exception.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult badRequestHandler(Exception e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class})
    public ErrorResult unauthorizedHandler(Exception e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.UNAUTHORIZED);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult serverErrorHandler(Exception e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResult handleException(Exception e, HttpServletRequest request, HttpStatus status) {
        String clientIp = request.getRemoteAddr();
        String userId = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : "Anonymous";

        String requestMethod = request.getMethod();
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();

        String errorMessage = status == HttpStatus.INTERNAL_SERVER_ERROR ? "INTERNAL_SERVER_ERROR" : e.getMessage();
        Level logLevel = status == HttpStatus.INTERNAL_SERVER_ERROR ? Level.ERROR : Level.WARN;

        log.atLevel(logLevel).log("사용자: {}, IP: {}, 요청: {} {}?{}, 상태 코드: {}, 에러 메시지: {}",
            userId, clientIp, requestMethod, requestUri, queryString, status.value(), errorMessage, e);

        return new ErrorResult(status.value(), errorMessage, requestMethod, requestUri);
    }
}