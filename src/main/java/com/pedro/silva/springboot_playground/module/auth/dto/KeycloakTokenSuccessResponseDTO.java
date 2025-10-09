package com.pedro.silva.springboot_playground.module.auth.dto;

import lombok.Value;

@Value
public class KeycloakTokenSuccessResponseDTO {
    String access_token;

    String refresh_token;

    String token_type;

    String session_state;

    String scope;

    int expires_in;

    int refresh_expires_in;

    int not_before_policy;
}
