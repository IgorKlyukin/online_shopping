package com.example.online_shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "t_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartEntity> cartEntities;

    private Long cartCookie;

    public Cart() {}

    public Cart(User user) {
        this.user = user;
    }

    public Cart(Long cartCookie) {
        this.cartCookie = cartCookie;
    }
}
