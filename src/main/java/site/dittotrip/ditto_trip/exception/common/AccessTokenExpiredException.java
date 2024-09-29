package site.dittotrip.ditto_trip.exception.common;

public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException() {
        super();
    }

    public AccessTokenExpiredException(String message) {
        super(message);
    }
}
