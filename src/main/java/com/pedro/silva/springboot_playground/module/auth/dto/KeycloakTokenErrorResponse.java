package com.pedro.silva.springboot_playground.module.auth.dto;

import lombok.Value;

@Value
public class KeycloakTokenErrorResponse {
    String error;

    String error_description;
}
