package com.pedro.silva.springboot_playground.module.product;

import com.pedro.silva.springboot_playground.module.product.dto.CreateProductRequest;
import com.pedro.silva.springboot_playground.module.product.dto.EditProductRequest;
import com.pedro.silva.springboot_playground.module.product.exception.ProductNotFoundException;
import com.pedro.silva.springboot_playground.module.product.mapper.CreateProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final CreateProductMapper createProductMapper;

    public Page<Product> findProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> specification = ProductSpecification.withFilters(name, minPrice, maxPrice);

        return productRepository.findAll(specification, pageable);
    }

    public Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));
    }

    public Product create(CreateProductRequest request) {
        return productRepository.save(createProductMapper.toEntity(request));
    }

    @Transactional
    public Product edit(UUID id, EditProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));

        if (request.getName() != null) {
            product.setName(request.getName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getImage() != null) {
            product.setImage(request.getImage());
        }

        return productRepository.save(product);
    }

    @Transactional
    public Product delete(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));

        productRepository.delete(product);

        return product;
    }

    @Transactional
    public Product softDelete(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found."));

        product.setActive(false);
        product.setDeletedAt(LocalDateTime.now());

        return productRepository.save(product);
    }
}
