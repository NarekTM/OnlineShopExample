package com.example.onlineshop.controller.helper;

import com.example.onlineshop.response.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class ResponseCreator {
    public ResponseEntity<CustomResponse> createResponseEntityWithStatus(CustomResponse customResponse) {
        HttpStatus status = setStatus(customResponse.getFailureMessages());

        return new ResponseEntity<>(customResponse, status);
    }

    private HttpStatus setStatus(List<?> failureMessages) {
        return failureMessages == null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }
}
