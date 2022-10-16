package com.example.onlineshop.service;

import com.example.onlineshop.domain.entity.*;
import com.example.onlineshop.dto.*;
import com.example.onlineshop.mapper.*;
import com.example.onlineshop.repository.*;
import com.example.onlineshop.response.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProductCategoryServiceTest {
    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private ProductCategoryService productCategoryService;

    @DisplayName("Test for createProductCategory method.")
    @Test
    public void givenProductCategoryObject_whenCreateProductCategory_thenReturnProductCategoryDtoObject(){
        ProductCategoryCreateDto dto = new ProductCategoryCreateDto("Furniture");
        ProductCategory productCategory = ProductCategory.builder().name("Furniture").id(1L).build();

        // given - precondition or setup
        given(productCategoryRepository.save(productCategory)).willReturn(productCategory);
        given(globalMapper.map(dto, ProductCategory.class)).willReturn(productCategory);
        given(globalMapper.map(productCategory, ProductCategoryDto.class)).willReturn(new ProductCategoryDto(
                productCategory.getId(), productCategory.getName()
        ));

        // when -  action or the behaviour that we are going test
        ProductCategoryResponse response = productCategoryService.createProductCategory(dto);

        // then - verify the output
        assertThat(response.getProductCategoryDto()).isNotNull();
        assertThat(response.getProductCategoryDto().getName()).isEqualTo(productCategory.getName());
        assertThat(response.getProductCategoryDto().getId()).isEqualTo(productCategory.getId());
    }

    @DisplayName("Test for createProductCategory method which throws exception.")
    @Test
    public void givenProductCategoryObject_whenCreateSameProductCategory_thenThrowException(){
        ProductCategoryCreateDto dto = new ProductCategoryCreateDto("Furniture");
        ProductCategory productCategory = ProductCategory.builder().name("Furniture").id(1L).build();

        // given - precondition or setup
        given(productCategoryRepository.existsByName(productCategory.getName())).willReturn(true);

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> productCategoryService.createProductCategory(dto));

        // then - verify the output
        verify(productCategoryRepository, never()).save(any(ProductCategory.class));
    }
}
