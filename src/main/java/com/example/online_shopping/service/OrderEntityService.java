package com.example.online_shopping.service;

import com.example.online_shopping.entity.CartEntity;
import com.example.online_shopping.entity.Order;
import com.example.online_shopping.entity.OrderEntity;
import com.example.online_shopping.repository.OrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderEntityService {
    
    private final OrderEntityRepository orderEntityRepository;

    public void addOrderEntities(Order order, Set<CartEntity> cartEntities) {
        Set<OrderEntity> orderEntityList = new HashSet<>();
        cartEntities.forEach(cartEntity -> orderEntityList.add(new OrderEntity(cartEntity, order)));
        orderEntityRepository.saveAll(orderEntityList);
        BigDecimal sum = orderEntityList.stream()
                .map(orderEntity -> orderEntity.getPrice().multiply(BigDecimal.valueOf(orderEntity.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);
        order.setSumTotal(sum);
    }

    public Set<OrderEntity> findByOrder(Order order) {
        return orderEntityRepository.findByOrder(order);
    }
}
