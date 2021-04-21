package com.example.online_shopping.service;

import com.example.online_shopping.entity.Category;
import com.example.online_shopping.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Set<Category> getTopCategories() {
        return categoryRepository.findCategoriesByParentIsNull();
    }

    public Set<Category> getLowerCategoriesByCategoryId(Long id) {
        return categoryRepository.findCategoriesByParentId(id);
    }

    public Set<Category> getFinalCategoriesByCategoryId(Long id) {
        return categoryRepository.findFinalCategoriesByCategoryId(id);
    }

    public List<Category> getBreadCrumbs(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null && category.getParent().size() != 0) {
            Category parent = category.getParent().stream().findFirst().orElse(null);
            if (parent != null) {
                List<Category> list = getBreadCrumbs(parent.getId());
                list.add(category);
                return list;
            }
        }
        return new ArrayList<>(category == null ? Collections.emptyList() : Collections.singleton(category));
    }
}
