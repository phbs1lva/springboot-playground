package com.pedro.silva.springboot_playground.module.product;

import com.pedro.silva.springboot_playground.module.product.dto.CreateProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Specification<ProductSpecification>> specificationArgumentCaptor;

    private Product product;
    private Product product2;

    @BeforeEach
    void setup() {
        product = new Product();
        product.setName("Nvidia 5090");
        product.setImage("http://image.com");
        product.setPrice(new BigDecimal("150"));
        product.setActive(true);
        product.setId(UUID.randomUUID());

        product2 = new Product();
        product2.setName("AMD");
        product2.setImage("http://image.com");
        product2.setPrice(new BigDecimal("300"));
        product2.setActive(true);
        product2.setId(UUID.randomUUID());
    }

    @Test
    void findProducts_WithAllParameters_ReturnsFilteredProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> mockList = Collections.singletonList(product);
        Page<Product> expectedPage = new PageImpl<>(mockList, pageable, mockList.size());

        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(expectedPage);

        Page<Product> resultPage = productService.findProducts("nvidia", new BigDecimal("20"), new BigDecimal("150"), pageable);

        verify(productRepository, times(1)).findAll(specificationArgumentCaptor.capture(), eq(pageable));

        assertNotNull(resultPage, "The result page should not be null");
        assertEquals(1, resultPage.getTotalElements(), "The page should contain 1 element");
        assertEquals(expectedPage.getContent(), resultPage.getContent(), "The content should match the expected list");

        Specification<Product> capturedSpec = specificationArgumentCaptor.getValue();
        assertNotNull(capturedSpec, "The Specification object should have been created and captured.");
    }
}
