package dev.shuktika.processpension.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final Integer code;
    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(Exception e, HttpStatus httpStatus) {
        this(httpStatus.value(), httpStatus, e.getMessage(), LocalDateTime.now());
    }
}

