package com.example.online_shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "t_order")
public class Order extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order")
    private Set<OrderEntity> orderEntities;

    private BigDecimal sumTotal;

    public Order() {}

    public Order(User user) {
        this.user = user;
    }
}
