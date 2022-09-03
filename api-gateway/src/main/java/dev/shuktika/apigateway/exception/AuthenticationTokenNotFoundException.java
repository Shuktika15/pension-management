package dev.shuktika.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationTokenNotFoundException extends RuntimeException {
    public AuthenticationTokenNotFoundException(String msg) {
        super(msg);
    }
}
