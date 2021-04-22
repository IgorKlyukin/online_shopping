package com.example.online_shopping.service;

import com.example.online_shopping.entity.Cart;
import com.example.online_shopping.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCartByCookie(Long value) {
        return cartRepository.findFirstByCartCookie(value);
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public void delete(Cart cart) {
        cartRepository.deleteById(cart.getId());
    }
}