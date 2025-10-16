package com.pedro.silva.springboot_playground.module.product.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class GetProductResponse {
    UUID id;

    String name;

    BigDecimal price;

    String image;

    Boolean active;
}
