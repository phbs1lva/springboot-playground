package com.pedro.silva.springboot_playground.module.auth.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class SignUpResponseDTO {
    String username;

    UUID id;
}
