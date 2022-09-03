package dev.shuktika.pensionerdetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PensionerNotFoundException extends RuntimeException {
    public PensionerNotFoundException(String msg) {
        super(msg);
    }
}
