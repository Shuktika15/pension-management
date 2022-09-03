package dev.shuktika.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class RequestTimeoutException extends RuntimeException {
    public RequestTimeoutException(String msg) {
        super(msg);
    }
}