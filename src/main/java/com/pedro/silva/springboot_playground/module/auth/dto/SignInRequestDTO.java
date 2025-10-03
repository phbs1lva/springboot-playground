package com.pedro.silva.springboot_playground.module.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequestDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
