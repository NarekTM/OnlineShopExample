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
public class ProductBasketService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductBasketRepository productBasketRepository;
    private final GlobalMapper globalMapper;

    public ProductBasketService(ProductRepository productRepository,
                                UserRepository userRepository,
                                ProductBasketRepository productBasketRepository,
                                GlobalMapper globalMapper) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productBasketRepository = productBasketRepository;
        this.globalMapper = globalMapper;
    }

    public ProductBasketResponse addProductToBasket(Long productId, String username) {
        Product product = productRepository.getReferenceById(productId);
        Optional<User> user = userRepository.findByUsername(username);
        Basket basket = user.get().getBasket();

        ProductBasket productBasket = new ProductBasket();
        productBasket.setProduct(product);
        productBasket.setBasket(basket);
        productBasket.setQuantity(1);

        ProductBasket savedProductBasket = productBasketRepository.save(productBasket);

        return new ProductBasketResponse(globalMapper.map(savedProductBasket, ProductBasketDto.class));
    }

    public ProductBasketResponse addQuantityByOne(Long productBasketId) {
        ProductBasket productBasket = productBasketRepository.getReferenceById(productBasketId);
        int quantity = productBasket.getQuantity();
        productBasket.setQuantity(quantity + 1);

        ProductBasket savedProductBasket = productBasketRepository.save(productBasket);

        return new ProductBasketResponse(globalMapper.map(savedProductBasket, ProductBasketDto.class));
    }

    public ProductBasketResponse subtractQuantityByOne(Long productBasketId) {
        ProductBasketResponse productBasketResponse = new ProductBasketResponse();
        ProductBasket productBasket = productBasketRepository.getReferenceById(productBasketId);

        int quantity = productBasket.getQuantity();
        if (quantity <= 1) {
            String message = "There is not enough quantity to subtract.";
            addFailureMessageToResponse(productBasketResponse, message);
        } else {
            productBasket.setQuantity(quantity - 1);
            ProductBasket savedProductBasket = productBasketRepository.save(productBasket);
            productBasketResponse.setProductBasketDto(globalMapper.map(savedProductBasket, ProductBasketDto.class));
        }

        return productBasketResponse;
    }

    public ProductBasketResponse deleteProductFromBasket(Long productBasketId) {
        ProductBasketResponse productBasketResponse = new ProductBasketResponse();

        try {
            ProductBasket productBasketToDelete = productBasketRepository.getReferenceById(productBasketId);
            productBasketRepository.deleteById(productBasketId);
            productBasketResponse.setProductBasketDto(globalMapper.map(productBasketToDelete, ProductBasketDto.class));
        } catch (EmptyResultDataAccessException ex) {
            addFailureMessageToResponse(productBasketResponse, ex.getMessage());
        }

        return productBasketResponse;
    }

    private void addFailureMessageToResponse(CustomResponse customResponse, String message) {
        List<String> failureMessages = new ArrayList<>();
        failureMessages.add(message);
        customResponse.setFailureMessages(failureMessages);
    }
}
