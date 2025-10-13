package com.pedro.silva.springboot_playground.module.auth.dto;

import lombok.Value;

import java.util.List;

@Value
public class KeycloakUserRequest {
    String username;

    String email;

    String firstName;

    String lastName;

    Boolean enabled;

    Boolean emailVerified;

    List<KeycloakCredential> credentials;
}
