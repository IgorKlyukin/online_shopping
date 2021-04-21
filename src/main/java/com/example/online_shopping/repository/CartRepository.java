package com.example.online_shopping.repository;

import com.example.online_shopping.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findFirstByCartCookie(Long cartCookie);
}
