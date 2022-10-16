package com.example.onlineshop.response;

import com.example.onlineshop.dto.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryResponse extends CustomResponse {
    private ProductCategoryDto productCategoryDto;
}
