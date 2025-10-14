package com.pedro.silva.springboot_playground.shared.handler;

import com.pedro.silva.springboot_playground.shared.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                "An unexpected error occurred: " + ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorDTO, status);
    }
}
