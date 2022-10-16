package com.example.onlineshop.response;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericPageableResponse<T> {
    private List<T> data;

    private Integer totalPageCount;

    private Integer currentPageNumber;
}
