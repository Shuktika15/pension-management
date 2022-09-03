package dev.shuktika.authorization.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class BadCredentialsAuthenticationException extends RuntimeException {
    public BadCredentialsAuthenticationException(String msg) {
        super(msg);
    }
}