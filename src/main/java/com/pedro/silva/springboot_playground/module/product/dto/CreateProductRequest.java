package com.pedro.silva.springboot_playground.module.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
public class CreateProductRequest {
    @NotBlank
    String name;

    @Positive
    BigDecimal price;

    @NotBlank
    String image;

    @NotNull
    Boolean active;

    LocalDateTime deletedAt;
}
