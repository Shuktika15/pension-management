package dev.shuktika.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidJWTException extends RuntimeException {
    public InvalidJWTException(String msg) {
        super(msg);
    }
}