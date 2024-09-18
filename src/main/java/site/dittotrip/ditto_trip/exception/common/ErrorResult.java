package site.dittotrip.ditto_trip.exception.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResult {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int code;
    private String message;
    private String method;
    private String path;

    public ErrorResult(int code, String message, String method, String path) {
        this.code = code;
        this.message = message;
        this.method = method;
        this.path = path;
    }
}
