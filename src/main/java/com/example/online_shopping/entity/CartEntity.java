package com.example.online_shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "t_cart_entity")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Cart cart;

    private Integer count = 0;

    public CartEntity() {}

    public CartEntity(Product product, Cart cart) {
        this.product = product;
        this.cart = cart;
    }
}
