package com.example.online_shopping.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, message = "Не меньше 5 знаков")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Category> parent;

    @ManyToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Category> children;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Product> products;
}
