package com.bikkadit.electronic.store.exceptions;

import com.bikkadit.electronic.store.helper.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;


import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import java.util.Map;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandling {

    // Exception handling for ResourceNotFoundException

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandling(ResourceNotFoundException ex) {
        log.info("Request Starting for Handle  ResourceNotFoundException ");

        ApiResponse responce = ApiResponse
                .builder()
                .message(ex.getMessage())
                .success(true).build();
        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
    }


    //Exception handling for validationException :MethodArgumentNotValidException

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandling(MethodArgumentNotValidException ex) {
        log.info("Request Starting for Handle MethodArgumentNotValidException ");
        Map<String, String> map = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String msg = error.getDefaultMessage();
            String field = ((FieldError) error).getField();
            map.put(field, msg);
        });
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }


    //handle bad request api exception
    @ExceptionHandler(BadRequestApiException.class)
    public ResponseEntity<ApiResponse> badrequestApiExceptionHandling(BadRequestApiException ex) {
        log.info("Request Starting for Handle  BadRequestApiException ");

        ApiResponse responce = ApiResponse
                .builder()
                .message(ex.getMessage())
                .success(false).build();
        return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
    }
}
