package com.joseph.service.Impl;

import com.joseph.entity.Category;
import com.joseph.entity.Customer;
import com.joseph.entity.Product;
import com.joseph.repository.CategoryRepository;
import com.joseph.repository.CustomerRepository;
import com.joseph.repository.ProductRepository;
import com.joseph.service.CustomerService;
import com.joseph.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// import com.joseph.exception.ResourceNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("Customer with ID = ${id} is not found"));
    };


    @Override
    @Transactional
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();

        if (product != null) {
            Category category = product.getCategory();

            // Remove associations

            // Remove the product from the category's products
            category.getProducts().remove(product);

            // Save the customer to update the changes
            categoryRepository.save(category);

            // Delete the order
            productRepository.delete(product);
        }

    }
}