package com.pedro.silva.springboot_playground.module.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SignUpRequestDTO {
    @NotBlank
    String username;

    @NotBlank
    String password;
}
