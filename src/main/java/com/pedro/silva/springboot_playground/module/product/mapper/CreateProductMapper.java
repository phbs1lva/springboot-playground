package com.pedro.silva.springboot_playground.module.product.mapper;

import com.pedro.silva.springboot_playground.module.product.Product;
import com.pedro.silva.springboot_playground.module.product.dto.CreateProductRequest;
import com.pedro.silva.springboot_playground.module.product.dto.CreateProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateProductMapper {
    CreateProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    Product toEntity(CreateProductRequest request);
}
