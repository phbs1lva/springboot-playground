package com.pedro.silva.springboot_playground.module.auth.dto;

public record KeycloakCredential(String type, String value, Boolean temporary) {
}
