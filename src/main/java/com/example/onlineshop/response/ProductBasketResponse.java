package com.example.onlineshop.response;

import com.example.onlineshop.dto.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBasketResponse extends CustomResponse {
    private ProductBasketDto productBasketDto;
}
