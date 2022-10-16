package com.example.onlineshop.dto;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDto {
    @NotNull
    private String name;

    private Long price;

    private Long count;

    @NotNull
    private Long productCategoryId;
}
