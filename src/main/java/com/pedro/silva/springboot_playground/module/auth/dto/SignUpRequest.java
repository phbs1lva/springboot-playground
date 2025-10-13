package com.pedro.silva.springboot_playground.module.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class SignUpRequest {
    @NotBlank
    String username;

    @NotBlank
    String password;

    @NotBlank
    String email;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;
}
