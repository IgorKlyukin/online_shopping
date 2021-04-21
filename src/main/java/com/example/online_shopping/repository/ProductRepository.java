package com.example.online_shopping.repository;

import com.example.online_shopping.entity.Category;
import com.example.online_shopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> findDistinctProductByCategoriesIn(Set<Category> categories);
}
