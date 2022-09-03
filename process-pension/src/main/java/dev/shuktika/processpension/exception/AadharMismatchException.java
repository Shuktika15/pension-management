package dev.shuktika.processpension.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AadharMismatchException extends RuntimeException {
    public AadharMismatchException(String msg) {
        super(msg);
    }
}
