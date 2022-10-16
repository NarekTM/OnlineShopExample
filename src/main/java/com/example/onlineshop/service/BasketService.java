package com.example.onlineshop.service;

import com.example.onlineshop.domain.entity.*;
import com.example.onlineshop.dto.*;
import com.example.onlineshop.mapper.*;
import com.example.onlineshop.repository.*;
import com.example.onlineshop.response.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class BasketService {
    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final GlobalMapper globalMapper;

    public BasketService(BasketRepository basketRepository,
                         UserRepository userRepository,
                         GlobalMapper globalMapper) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.globalMapper = globalMapper;
    }

    public BasketResponse getBasket(String username) {
        BasketResponse basketResponse = new BasketResponse();
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            String message = String.format("User with username '%s' not found.", username);
            addFailureMessageToResponse(basketResponse, message);

            return basketResponse;
        }

        Basket basket = user.get().getBasket();
        basketResponse.setBasketDto(globalMapper.map(basket, BasketDto.class));

        return basketResponse;
    }

    private void addFailureMessageToResponse(CustomResponse customResponse, String message) {
        List<String> failureMessages = new ArrayList<>();
        failureMessages.add(message);
        customResponse.setFailureMessages(failureMessages);
    }
}
