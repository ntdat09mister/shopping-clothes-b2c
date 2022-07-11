package com.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends BaseController {
//    @ExceptionHandler({HttpMessageNotReadableException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
//        return failedResponse(HttpStatus.BAD_REQUEST.value() + "", "FormatException : Some field values are not valid");
//    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(EmptyResultDataAccessException e) {
        return failedResponse(HttpStatus.BAD_REQUEST.value() + "", "EmptyResultDataAccessException : Cart ID is not exist");
    }

    @ExceptionHandler({JpaSystemException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(JpaSystemException e) {
        return failedResponse(HttpStatus.BAD_REQUEST.value() + "", "JpaSystemException : User ID null or not exist");
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleException(MethodArgumentTypeMismatchException e) {
        return failedResponse(HttpStatus.BAD_REQUEST.value() + "", "MethodArgumentTypeMismatchException : Wrong input format");
    }

//    @ExceptionHandler({IllegalArgumentException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<?> handleException(IllegalArgumentException e) {
//        return failedResponse(HttpStatus.BAD_REQUEST.value() + "", "IllegalArgumentException : Wrong input format");
//    }

//    @ExceptionHandler({Exception.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<?> handleException(Exception e) {
//        log.error(e.getMessage(), e);
//        return failedResponse(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", String.format("%s: %s", e.getClass().getSimpleName(), e.getMessage()));
//    }
}
