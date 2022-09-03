package dev.shuktika.pensionerdetail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ParsingCSVException extends RuntimeException {
    public ParsingCSVException(String msg) {
        super(msg);
    }
}
