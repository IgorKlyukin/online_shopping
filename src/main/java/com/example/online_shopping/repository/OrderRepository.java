package com.example.online_shopping.repository;

import com.example.online_shopping.entity.Order;
import com.example.online_shopping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> findOrdersByUser(User user);
    Order findByIdAndUser(Long id, User user);
}
