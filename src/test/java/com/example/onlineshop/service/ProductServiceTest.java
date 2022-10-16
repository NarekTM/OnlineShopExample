package com.example.onlineshop.service;

import com.example.onlineshop.domain.entity.*;
import com.example.onlineshop.dto.*;
import com.example.onlineshop.mapper.*;
import com.example.onlineshop.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private ProductService productService;

    @DisplayName("Test for createProduct method.")
    @Test
    public void givenProductObject_whenCreateProduct_thenReturnProductDtoObject() {
        // Arrange
        final var productCreteDto =
                ProductCreateDto.builder()
                        .name("Table")
                        .price(1000L)
                        .count(10L)
                        .productCategoryId(1L)
                        .build();

        final var productCategory =
                ProductCategory.builder()
                        .id(1L)
                        .name("Furniture")
                        .build();

        final var productToSave =
                Product.builder()
                        .id(1L)
                        .name("Table")
                        .price(1000L)
                        .count(10L)
                        .productCategory(productCategory)
                        .build();

        when(productRepository.save(any(Product.class))).thenReturn(productToSave);
        when(productCategoryRepository.findById(any(Long.class))).thenReturn(Optional.of(productCategory));
        when(globalMapper.map(productToSave, ProductDto.class)).thenReturn(
                new ProductDto(
                        productToSave.getId(),
                        productToSave.getName(),
                        productToSave.getPrice(),
                        productToSave.getCount()
                ));

        // Act
        final var actual = productService.createProduct(productCreteDto);

        // Assert
        assertThat(actual.getProductDto().getName()).isEqualTo(productToSave.getName());
        verify(productRepository, times(1)).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }
}
