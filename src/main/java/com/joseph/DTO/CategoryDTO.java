package com.joseph.DTO;

import com.joseph.entity.Product;

import java.util.List;

public class CategoryDTO {
    private Long id;
    private String name;
    private List<ProductDTO> products;
    // Getters and setters...


    public CategoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
