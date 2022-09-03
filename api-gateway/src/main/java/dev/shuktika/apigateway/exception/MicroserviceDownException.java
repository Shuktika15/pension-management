package dev.shuktika.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class MicroserviceDownException extends RequestTimeoutException {
    public MicroserviceDownException(String msg) {
        super(msg);
    }
}
