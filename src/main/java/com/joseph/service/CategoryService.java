package com.joseph.service;

import com.joseph.entity.Category;
import com.joseph.entity.Customer;

import java.util.List;
// import com.joseph..exception.ResourceNotFoundException;

public interface CategoryService {

    Category saveCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void deleteCategory(Long id);

}