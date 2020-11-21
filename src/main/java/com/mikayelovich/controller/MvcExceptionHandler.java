package com.mikayelovich.controller;

import com.mikayelovich.util.ApiError;
import com.mikayelovich.util.exception.JwtAuthenticationException;
import com.mikayelovich.util.exception.NotFoundException;
import com.mikayelovich.util.exception.ServerSideException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundExceptions(NotFoundException exception) {
        return new ResponseEntity<>(
                new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServerSideException.class)
    public ResponseEntity<ApiError> handleServerSideExceptions(ServerSideException exception) {
        return new ResponseEntity<>(
                new ApiError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ApiError> handleJwtAuthenticationExceptions(JwtAuthenticationException exception) {
        return new ResponseEntity<>(
                new ApiError(exception.getMessage(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }





}

