package com.example.online_shopping.service;

import com.example.online_shopping.entity.Order;
import com.example.online_shopping.entity.User;
import com.example.online_shopping.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Set<Order> getOrderAll(User user) {
        return orderRepository.findOrdersByUser(user);
    }

    public Order getOrderByIdAndUser(Long id, User user) {
        return orderRepository.findByIdAndUser(id, user);
    }

    public Order createOrder(User user) {
        Order order = new Order(user);
        updateOrder(order);
        return order;
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }
}