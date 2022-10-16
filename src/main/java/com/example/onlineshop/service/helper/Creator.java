package com.example.onlineshop.service.helper;

import com.example.onlineshop.criteria.*;
import org.springframework.data.domain.*;

public class Creator {
    public static PageRequest createPageRequest(SearchCriteria searchCriteria) {
        Integer pageNumber = searchCriteria.getPageNumber();
        Integer pageSize = searchCriteria.getPageSize();

        String sortField = searchCriteria.getSortField();
        if (sortField == null || sortField.isEmpty()) {
            sortField = "name";
        }

        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(searchCriteria.getSortDirection());
        } catch (Exception e) {
            sortDirection = Sort.Direction.ASC;
        }

        return PageRequest.of(pageNumber, pageSize,
                Sort.by(sortDirection, sortField));
    }
}
