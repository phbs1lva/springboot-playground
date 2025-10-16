package com.pedro.silva.springboot_playground.shared.handler;

import com.pedro.silva.springboot_playground.shared.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthorizationDeniedException(Exception ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                "Access denied",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorDTO, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                "Validation failed - " + errors.toString(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponseDTO, status);
    }
}
