package com.client.ws.rasmooplus.exception.handler;

import com.client.ws.rasmooplus.dto.error.ErrorResponseDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ResourceHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> notFoundException(NotFoundException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> badRequestException(BadRequestException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> messages = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();

            messages.put(field, defaultMessage);
        });

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                Arrays.toString(messages.entrySet().toArray()),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> dataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> businessException(BusinessException e) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), HttpStatus.CONFLICT, HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> httpClientErrorException(HttpClientErrorException e) {
        HttpStatus httpStatus = HttpStatus.resolve(e.getStatusCode().value());
        ErrorResponseDto errorResponse = new ErrorResponseDto(e.getMessage(), httpStatus,
                Objects.nonNull(httpStatus) ? httpStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(Objects.nonNull(httpStatus) ? httpStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(errorResponse);
    }



}
