package site.dittotrip.ditto_trip.exception.common;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException() {
        super();
    }

    public RefreshTokenExpiredException(String message) {
        super(message);
    }
}