package dev.shuktika.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BadTokenException extends RuntimeException {
    public BadTokenException(String msg) {
        super(msg);
    }
}
