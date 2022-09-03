package dev.shuktika.pensionerdetail.controller;

import dev.shuktika.pensionerdetail.exception.ParsingCSVException;
import dev.shuktika.pensionerdetail.exception.PensionerNotFoundException;
import dev.shuktika.pensionerdetail.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ParsingCSVException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleParsingCSVException(ParsingCSVException e) {
        return new ResponseEntity<>(new ErrorResponse(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PensionerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handlePensionerNotFoundException(PensionerNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
