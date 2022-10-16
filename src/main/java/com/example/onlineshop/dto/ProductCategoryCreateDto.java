package com.example.onlineshop.dto;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryCreateDto {
    @NotNull
    private String name;
}
