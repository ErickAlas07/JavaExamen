package com.erick.examencursojava.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "is required")
    @NotNull(message = "is mandatory")
    @Column(name = "category_name")
    private String name;

    @NotBlank(message = "is required")
    @NotBlank(message = "is required")
    @Column(name = "category_type")
    private String type;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category")
    @JsonIgnore
    private Set<Product> products;

    public Category() {
        products = new HashSet<>();
    }

    public Category(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
