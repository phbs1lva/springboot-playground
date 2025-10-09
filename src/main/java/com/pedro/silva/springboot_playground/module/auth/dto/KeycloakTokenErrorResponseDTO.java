package com.pedro.silva.springboot_playground.module.auth.dto;

import lombok.Value;

@Value
public class KeycloakTokenErrorResponseDTO {
    String error;

    String error_description;
}
