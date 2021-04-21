package com.example.online_shopping.service;

import com.example.online_shopping.entity.Order;
import com.example.online_shopping.entity.User;
import com.example.online_shopping.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

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