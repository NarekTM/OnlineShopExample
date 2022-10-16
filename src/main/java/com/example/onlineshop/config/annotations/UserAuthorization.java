package com.example.onlineshop.config.annotations;

import org.springframework.security.access.prepost.*;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasRole(T(com.example.onlineshop.domain.enums.Role).USER)")
public @interface UserAuthorization {
}
