package com.example.online_shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "t_order_entity")
public class OrderEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @NotNull
    private BigDecimal price;

    @ManyToOne
    private Order order;

    private Integer count;

    public OrderEntity() {}

    public OrderEntity(CartEntity cartEntity, Order order) {
        this.product = cartEntity.getProduct();
        this.price = cartEntity.getProduct().getPrice();
        this.order = order;
        this.count = cartEntity.getCount();
    }
}
