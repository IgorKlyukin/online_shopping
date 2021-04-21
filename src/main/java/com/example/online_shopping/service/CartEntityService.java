package com.example.online_shopping.service;

import com.example.online_shopping.entity.Cart;
import com.example.online_shopping.entity.CartEntity;
import com.example.online_shopping.entity.Product;
import com.example.online_shopping.repository.CartEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class CartEntityService {

    @Autowired
    private CartEntityRepository cartEntityRepository;

    public static final String ZERO = "0";

    public void addProductToCart(Cart cart, Product product) {
        if (product == null) {
            return;
        }
        CartEntity cartEntity = cartEntityRepository.findFirstByCartAndProduct(cart, product);
        if (cartEntity == null) {
            cartEntity = new CartEntity(product, cart);
        }

        changeProductToCart(cartEntity, true);
    }

    public void addFromCartToCart(Cart from, Cart to) {
        Set<CartEntity> byCart = findByCart(from);
        byCart.forEach(cartEntity -> cartEntity.setCart(to));
        cartEntityRepository.saveAll(byCart);
    }

    public String getAllCount(Cart cart) {
        Integer integer = cartEntityRepository.sumCartEntityCountByCart(cart);
        return integer == null ? ZERO : String.valueOf(integer);
    }

    public String getAllSum(Cart cart) {
        BigDecimal bigDecimal = cartEntityRepository.sumPriceProductByCart(cart);
        return bigDecimal == null ? ZERO : String.valueOf(bigDecimal.doubleValue());
    }

    public Boolean deleteCartEntity(Long id) {
        if (cartEntityRepository.findById(id).isPresent()) {
            cartEntityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String enlargeToCart(Long id) {
        return cartEntityRepository.findById(id)
                .map(cartEntity -> changeProductToCart(cartEntity, true)).orElse(ZERO);
    }

    public String reduceFromCart(Long id) {
        return cartEntityRepository.findById(id)
                .map(cartEntity -> changeProductToCart(cartEntity, false)).orElse(ZERO);
    }

    private String changeProductToCart(CartEntity cartEntity, boolean change) {
        int count = cartEntity.getCount() + (change ? 1 : -1);
        if (count == 0) {
            cartEntityRepository.delete(cartEntity);
        } else {
            cartEntity.setCount(count);
            cartEntityRepository.save(cartEntity);
        }
        return Integer.toString(count);
    }

    public Set<CartEntity> findByCart(Cart cart) {
        return cartEntityRepository.findByCart(cart);
    }
}
