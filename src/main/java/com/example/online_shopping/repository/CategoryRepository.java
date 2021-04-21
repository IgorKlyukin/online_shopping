package com.example.online_shopping.repository;

import com.example.online_shopping.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findCategoriesByParentIsNull();

    Set<Category> findCategoriesByParentId(Long id);

    @Query(value = "SELECT * FROM GET_FINAL_CATEGORIES_FROM_PARENT(:id);", nativeQuery = true)
    Set<Category> findFinalCategoriesByCategoryId(Long id);
}
