package com.example.online_shopping.service;

import com.example.online_shopping.entity.Cart;
import com.example.online_shopping.repository.CartEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CartEntityServiceTest {

    @MockBean
    private CartEntityRepository cartEntityRepository;

    @Autowired
    private CartEntityService cartEntityService;

    @Test
    void getAllCount() {
        //given
        Cart cart = new Cart();

        //when
        String allCount = cartEntityService.getAllCount(cart);

        //then
        Mockito.verify(cartEntityRepository, Mockito.times(1)).sumCartEntityCountByCart(cart);
        Assertions.assertEquals("0", allCount);
    }
}