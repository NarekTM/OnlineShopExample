package com.example.onlineshop.dto;

import com.example.onlineshop.domain.entity.*;
import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    private Long id;
    private List<ProductBasket> productsInBasket;
}
