package com.example.onlineshop.controller;

import com.example.onlineshop.config.annotations.*;
import com.example.onlineshop.controller.helper.*;
import com.example.onlineshop.response.*;
import com.example.onlineshop.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/baskets")
@AnyAuthorization
public class BasketController {
    private final BasketService basketService;
    private final ProductBasketService productBasketService;
    private final ResponseCreator responseCreator;

    public BasketController(BasketService basketService,
                            ProductBasketService productBasketService,
                            ResponseCreator responseCreator) {
        this.basketService = basketService;
        this.productBasketService = productBasketService;
        this.responseCreator = responseCreator;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getBasket(@PathVariable String username) {
        BasketResponse basketResponse = basketService.getBasket(username);

        return responseCreator.createResponseEntityWithStatus(basketResponse);
    }

    @PostMapping("/{productBasketId}/add")
    public ResponseEntity<?> addQuantityByOne(@PathVariable Long productBasketId) {
        ProductBasketResponse productBasketResponse = productBasketService.addQuantityByOne(productBasketId);

        return responseCreator.createResponseEntityWithStatus(productBasketResponse);
    }

    @PostMapping("/{productBasketId}/subtract")
    public ResponseEntity<?> subtractQuantityByOne(@PathVariable Long productBasketId) {
        ProductBasketResponse productBasketResponse = productBasketService.subtractQuantityByOne(productBasketId);

        return responseCreator.createResponseEntityWithStatus(productBasketResponse);
    }

    @DeleteMapping("/{productBasketId}")
    public ResponseEntity<?> deleteProductFromBasket(@PathVariable Long productBasketId) {
        ProductBasketResponse productBasketResponse = productBasketService.deleteProductFromBasket(productBasketId);

        return responseCreator.createResponseEntityWithStatus(productBasketResponse);
    }
}
