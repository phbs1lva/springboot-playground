package com.pedro.silva.springboot_playground.module.auth.handler;

import com.pedro.silva.springboot_playground.module.auth.exception.UserNotFoundException;
import com.pedro.silva.springboot_playground.module.user.exception.UserAlreadyExistsException;
import com.pedro.silva.springboot_playground.shared.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.pedro.silva.springboot_playground.module.auth")
public class AuthExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(Exception ex) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorDTO, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundInKeycloakException(Exception ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ErrorResponseDTO errorDTO = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorDTO, status);
    }
}
