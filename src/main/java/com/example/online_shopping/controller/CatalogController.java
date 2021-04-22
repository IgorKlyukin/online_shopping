package com.example.online_shopping.controller;

import com.example.online_shopping.entity.Category;
import com.example.online_shopping.service.CategoryService;
import com.example.online_shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @GetMapping("/root")
    public String showCatalogAll(Model model) {
        model.addAttribute("categories", categoryService.getTopCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "catalog";
    }

    @GetMapping("/{id}")
    public String showCatalogByCategoryId(@PathVariable("id") Long id, Model model) {
        model.addAttribute("breadCrumbs", categoryService.getBreadCrumbs(id));
        model.addAttribute("categories", categoryService.getLowerCategoriesByCategoryId(id));
        Set<Category> finalCategoriesByCategoryId = categoryService.getFinalCategoriesByCategoryId(id);
        model.addAttribute("products", productService.getProducts(finalCategoriesByCategoryId));
        return "catalog";
    }
}
