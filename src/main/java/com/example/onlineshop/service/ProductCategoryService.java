package com.example.onlineshop.service;

import com.example.onlineshop.domain.entity.*;
import com.example.onlineshop.dto.*;
import com.example.onlineshop.mapper.*;
import com.example.onlineshop.repository.*;
import com.example.onlineshop.response.*;
import org.hibernate.exception.*;
import org.springframework.dao.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final GlobalMapper globalMapper;


    public ProductCategoryService(ProductCategoryRepository productCategoryRepository,
                                  GlobalMapper globalMapper) {
        this.productCategoryRepository = productCategoryRepository;
        this.globalMapper = globalMapper;
    }

    public ProductCategoryResponse createProductCategory(ProductCategoryCreateDto productCategoryCreateDto) {
        ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();

        doCreate(productCategoryResponse, productCategoryCreateDto);

        return productCategoryResponse;
    }

    private void doCreate(ProductCategoryResponse productCategoryResponse,
                          ProductCategoryCreateDto productCategoryCreateDto) {
        if (productCategoryRepository.existsByName(productCategoryCreateDto.getName())) {
            String message = String.format(
                    "Product category with %s name exists.", productCategoryCreateDto.getName());
            addFailureMessageToResponse(productCategoryResponse, message);
            throw new DataIntegrityViolationException(message);
        }

        ProductCategory productCategoryToSave = globalMapper.map(productCategoryCreateDto, ProductCategory.class);

        ProductCategory savedProductCategory = productCategoryRepository.save(productCategoryToSave);
        productCategoryResponse.setProductCategoryDto(
                globalMapper.map(savedProductCategory, ProductCategoryDto.class));
    }

    private void addFailureMessageToResponse(CustomResponse customResponse, String message) {
        List<String> failureMessages = new ArrayList<>();
        failureMessages.add(message);
        customResponse.setFailureMessages(failureMessages);
    }
}
