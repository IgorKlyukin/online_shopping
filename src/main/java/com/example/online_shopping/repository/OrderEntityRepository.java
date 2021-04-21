package com.example.online_shopping.repository;

import com.example.online_shopping.entity.Order;
import com.example.online_shopping.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
    Set<OrderEntity> findByOrder(Order order);
}
