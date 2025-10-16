package com.pedro.silva.springboot_playground.module.product.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class EditProductRequest {
    String name;

    BigDecimal price;

    String image;
}
