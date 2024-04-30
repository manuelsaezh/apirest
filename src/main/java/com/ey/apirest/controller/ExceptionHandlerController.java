package com.ey.apirest.controller;

import com.ey.apirest.dto.ErrorDto;
import com.ey.apirest.exception.BusinessException;
import com.ey.apirest.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(UserNotFoundException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(HttpMessageNotReadableException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(IllegalArgumentException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(BusinessException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(HttpRequestMethodNotSupportedException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(NoResourceFoundException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(ExpiredJwtException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = SignatureException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(SignatureException ex) {
        ErrorDto errorDto = ErrorDto.builder().mensaje(ex.getMessage()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> exceptionHandler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorDto errorDto = ErrorDto.builder().mensaje(errors.toString()).build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}
