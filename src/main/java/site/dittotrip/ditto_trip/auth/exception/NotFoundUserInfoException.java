package site.dittotrip.ditto_trip.auth.exception;

public class NotFoundUserInfoException extends RuntimeException{
    public NotFoundUserInfoException() {
        super();
    }

    public NotFoundUserInfoException(String message) {
        super(message);
    }
}
