package com.example.online_shopping.service;

import com.example.online_shopping.entity.Category;
import com.example.online_shopping.entity.Product;
import com.example.online_shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Set<Product> getProducts(Set<Category> categories) {
        return productRepository.findDistinctProductByCategoriesIn(categories);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
