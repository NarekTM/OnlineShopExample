package com.example.onlineshop.repository;

import com.example.onlineshop.domain.entity.*;
import com.example.onlineshop.dto.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteProductById(Long id);

    /*
    @Query("select new com.example.onlineshop.dto.ProductDto(" +
            "p.id, " +
            "p.name, " +
            "p.price, " +
            "p.count) " +
            "from Product p left join fetch p.productCategory " +
            "where p.id = :id")
     */
    @Query("select new com.example.onlineshop.dto.ProductDto(" +
            "p.id, " +
            "p.name, " +
            "p.price, " +
            "p.count) " +
            "from Product p " +
            "where p.id = :id")
    Optional<ProductDto> findByIdMapToDto(Long id);

    boolean existsByNameAndProductCategory(String name, ProductCategory productCategory);
}
