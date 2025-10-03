package com.pedro.silva.springboot_playground.shared.dto;

import lombok.Value;

@Value
public class ErrorResponseDTO {
    int status;
    String error;
    String message;
    long timestamp;
}
