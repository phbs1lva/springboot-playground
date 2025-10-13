package com.pedro.silva.springboot_playground.module.auth.dto;

import lombok.Value;

@Value
public class KeycloakTokenSuccessResponse {
    String access_token;

    String refresh_token;

    int expires_in;

    int refresh_expires_in;
}
