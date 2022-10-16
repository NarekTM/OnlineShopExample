package com.example.onlineshop.domain.enums;

import org.springframework.security.core.*;

public enum Role implements GrantedAuthority {
    ADMIN, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
