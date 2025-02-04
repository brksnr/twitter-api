package com.example.demo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(ApiException apiException){
        logger.error("Exception occured: " , apiException);
        return new ResponseEntity<>(new ExceptionResponse(apiException.getMessage(),apiException.getHttpStatus().value(), LocalDateTime.now()),apiException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception exception){
        logger.error("Exception occured: " , exception);
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
