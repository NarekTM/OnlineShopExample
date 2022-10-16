package com.example.onlineshop.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "basket")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "basket", fetch = FetchType.LAZY, optional = false)
    private User user;

    @OneToMany(mappedBy = "basket", fetch = FetchType.EAGER)
    private List<ProductBasket> productsInBasket;
}
