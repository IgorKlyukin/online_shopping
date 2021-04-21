package com.example.online_shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "t_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, max = 255, message = "Не меньше 5 знаков")
    @NotNull
    private String name;

    @Size(min = 15, max = 255, message = "Не меньше 15 знаков")
    private String description;

    @NotNull
    private BigDecimal price;

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Set<Category> categories;
}
