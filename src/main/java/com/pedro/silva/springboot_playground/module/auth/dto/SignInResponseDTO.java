package com.pedro.silva.springboot_playground.module.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInResponseDTO {
    private String token;

    private long expiresIn;
}
