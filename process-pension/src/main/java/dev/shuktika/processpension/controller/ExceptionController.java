package dev.shuktika.processpension.controller;

import dev.shuktika.processpension.exception.AadharMismatchException;
import dev.shuktika.processpension.exception.BankTypeNotFoundException;
import dev.shuktika.processpension.exception.PensionTypeNotFoundException;
import dev.shuktika.processpension.exception.PensionerDetailServiceException;
import dev.shuktika.processpension.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AadharMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleAadharMismatchException(AadharMismatchException e) {
        return new ResponseEntity<>(new ErrorResponse(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BankTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleBankTypeNotFoundException(BankTypeNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PensionerDetailServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handlePensionerDetailServiceException(PensionerDetailServiceException e) {
        return new ResponseEntity<>(new ErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PensionTypeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handlePensionTypeNotFoundException(PensionTypeNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}
