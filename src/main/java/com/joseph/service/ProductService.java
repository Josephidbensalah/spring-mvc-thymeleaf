package com.joseph.service;

import com.joseph.entity.Customer;
import com.joseph.entity.Product;

import java.util.List;
// import com.joseph..exception.ResourceNotFoundException;

public interface ProductService {

    Product saveProduct(Product product);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    void deleteProduct(Long id);

}