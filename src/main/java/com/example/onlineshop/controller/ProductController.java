package com.example.onlineshop.controller;

import com.example.onlineshop.config.annotations.*;
import com.example.onlineshop.controller.helper.*;
import com.example.onlineshop.criteria.*;
import com.example.onlineshop.dto.*;
import com.example.onlineshop.response.*;
import com.example.onlineshop.service.*;
import io.swagger.v3.oas.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductBasketService productBasketService;
    private final ResponseCreator responseCreator;


    public ProductController(ProductService productService,
                             ProductBasketService productBasketService,
                             ResponseCreator responseCreator) {
        this.productService = productService;
        this.productBasketService = productBasketService;
        this.responseCreator = responseCreator;
    }

    @AdminAuthorization
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateDto productCreateDto) {
        ProductResponse productResponse = productService.createProduct(productCreateDto);

        return responseCreator.createResponseEntityWithStatus(productResponse);
    }

    @AnyAuthorization
    @GetMapping
    public ResponseEntity<?> getProducts(ProductSearchCriteria productSearchCriteria) {
        GenericPageableResponse<ProductDto> products =
                productService.getProducts(productSearchCriteria);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @AnyAuthorization
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        ProductResponse productResponse = productService.getProduct(productId);

        return responseCreator.createResponseEntityWithStatus(productResponse);
    }

    @AdminAuthorization
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                          @Valid @RequestBody ProductUpdateDto productUpdateDto) {
        ProductResponse productResponse = productService.updateProduct(productId, productUpdateDto);

        return responseCreator.createResponseEntityWithStatus(productResponse);
    }

    @AdminAuthorization
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        ProductResponse productResponse = productService.deleteProduct(productId);

        return responseCreator.createResponseEntityWithStatus(productResponse);
    }

    @Operation(summary = "Add product to basket.",
            description = "Adding product to basket."
    )
    @AdminAuthorization
    @PostMapping("/{productId}")
    public ResponseEntity<?> addProductToBasket(@PathVariable Long productId, @RequestBody String username) {
        ProductBasketResponse productBasketResponse = productBasketService.addProductToBasket(productId, username);

        return responseCreator.createResponseEntityWithStatus(productBasketResponse);
    }
}
