package com.example.onlineshop.domain.entity;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_basket")
public class ProductBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_basket__seq_gen")
    @SequenceGenerator(name = "product_basket__seq_gen", sequenceName = "product_basket__id_seq", allocationSize = 100)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_product_basket__product"))
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_product_basket__basket"))
    private Basket basket;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
