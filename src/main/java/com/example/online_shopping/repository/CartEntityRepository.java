package com.example.online_shopping.repository;

import com.example.online_shopping.entity.Cart;
import com.example.online_shopping.entity.CartEntity;
import com.example.online_shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface CartEntityRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findFirstByCartAndProduct(Cart cart, Product product);

    @Query("SELECT SUM(c.count) FROM CartEntity c WHERE c.cart = :cart")
    Integer sumCartEntityCountByCart(Cart cart);

    @Query("SELECT SUM(ce.count * p.price) FROM CartEntity ce " +
            "LEFT JOIN Product p ON p.id = ce.product.id " +
            "WHERE ce.cart = :cart")
    BigDecimal sumPriceProductByCart(Cart cart);

    Set<CartEntity> findByCart(Cart cart);
}
