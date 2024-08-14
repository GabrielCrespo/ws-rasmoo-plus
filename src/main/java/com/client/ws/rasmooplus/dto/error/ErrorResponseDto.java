package com.client.ws.rasmooplus.dto.error;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto(String message, HttpStatus httpStatus, Integer status) {
}
