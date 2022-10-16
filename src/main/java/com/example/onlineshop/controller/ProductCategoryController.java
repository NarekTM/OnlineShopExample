package com.example.onlineshop.controller;

import com.example.onlineshop.config.annotations.*;
import com.example.onlineshop.controller.helper.*;
import com.example.onlineshop.criteria.*;
import com.example.onlineshop.dto.*;
import com.example.onlineshop.response.*;
import com.example.onlineshop.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
@RequestMapping("/api/v1/product-categories")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    private final ResponseCreator responseCreator;

    public ProductCategoryController(ProductCategoryService productCategoryService,
                                     ResponseCreator responseCreator) {
        this.productCategoryService = productCategoryService;
        this.responseCreator = responseCreator;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createProductCategory(
            @Valid @RequestBody ProductCategoryCreateDto productCategoryCreateDto) {
        ProductCategoryResponse productCategoryResponse =
                productCategoryService.createProductCategory(productCategoryCreateDto);

        return responseCreator.createResponseEntityWithStatus(productCategoryResponse);
    }
}
