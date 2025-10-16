package com.pedro.silva.springboot_playground.module.product.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class CreateProductResponse {
    UUID id;

    String name;

    BigDecimal price;

    String image;
}
