package com.joseph.service.Impl;

import com.joseph.entity.Category;
import com.joseph.entity.Customer;
import com.joseph.repository.CategoryRepository;
import com.joseph.repository.CustomerRepository;
import com.joseph.service.CategoryService;
import com.joseph.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// import com.joseph.exception.ResourceNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category getCategoryById(Long id) throws NullPointerException {
        return categoryRepository.findById(id).orElseThrow(
                () -> new NullPointerException("Category with ID = ${id} is not found"));
    }

    @Override
    @Transactional
    public void deleteCategory(Long theId) {
        categoryRepository.deleteById(theId);
    }
}