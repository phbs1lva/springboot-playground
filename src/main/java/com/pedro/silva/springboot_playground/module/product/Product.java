package com.pedro.silva.springboot_playground.module.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String name;

    @Column(precision = 9, scale = 2, nullable = false)
    BigDecimal price;

    @Column(nullable = false)
    String image;

    @Column(nullable = false)
    Boolean active = true;

    @Column
    LocalDateTime deletedAt;
}
