package com.example.online_shopping.repository;

import com.example.online_shopping.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void theLowestCategoriesIsNotNullAndFalseIsEmpty() {
        //when
        Set<Category> categoriesByParentIsNull = categoryRepository.findCategoriesByParentIsNull();

        //then
        Assertions.assertNotNull(categoriesByParentIsNull);
        Assertions.assertFalse(categoriesByParentIsNull.isEmpty());
    }

    @Test
    void findFinalCategoriesByCategoryId() {
        //given
        Set<String> set = new HashSet<>(Arrays.asList("Огурцы", "Помидоры", "Апельсин",
                                                        "Яблоко", "Вишня", "Арбуз"));

        //when
        Set<Category> finalCategoriesByCategoryId = categoryRepository.findFinalCategoriesByCategoryId(1L);

        //then
        Assertions.assertNotNull(finalCategoriesByCategoryId);
        Assertions.assertFalse(finalCategoriesByCategoryId.isEmpty());
        Assertions.assertEquals(6, finalCategoriesByCategoryId.size());
        Assertions.assertTrue(finalCategoriesByCategoryId.stream()
                .map(Category::getName)
                .collect(Collectors.toSet())
                .containsAll(set));
    }
}
