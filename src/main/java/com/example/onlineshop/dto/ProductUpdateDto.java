package com.example.onlineshop.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {
    private String name;

    private Long price;

    private Long count;
}
