package dev.shuktika.authorization.controller;


import dev.shuktika.authorization.exception.AadharMismatchException;
import dev.shuktika.authorization.exception.BadRequestException;
import dev.shuktika.authorization.exception.InvalidJWTException;
import dev.shuktika.authorization.exception.PensionerDetailServiceException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(InvalidJWTException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleInvalidJWTException(InvalidJWTException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AadharMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleAadharMismatchException(AadharMismatchException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PensionerDetailServiceException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseEntity<Error> handlePensionerDetailServiceException(PensionerDetailServiceException e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Error> handleException(Exception e) {
        return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
