package com.example.onlineshop.repository;

import com.example.onlineshop.domain.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ProductBasketRepository extends JpaRepository<ProductBasket, Long> {
}
