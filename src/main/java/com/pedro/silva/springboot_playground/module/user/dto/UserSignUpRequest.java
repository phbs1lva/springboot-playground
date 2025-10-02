package com.pedro.silva.springboot_playground.module.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
