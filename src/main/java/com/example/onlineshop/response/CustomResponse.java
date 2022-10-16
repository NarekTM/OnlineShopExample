package com.example.onlineshop.response;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class CustomResponse {
    private List<String> failureMessages;
}
