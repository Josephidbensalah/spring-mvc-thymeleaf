package com.joseph.web;

import com.joseph.entity.Category;
import com.joseph.entity.Customer;
import com.joseph.service.CategoryService;
import com.joseph.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping({"/list", "/"})
    public String listCategories(Model theModel) {
        List<Category> theCategories = categoryService.getAllCategories();
        theModel.addAttribute("categories", theCategories);
        return "list-categories";
    }


    @GetMapping("/categoryForm")
    public String categoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categoryForm";
    }
    @GetMapping("/categoryForm/{id}")
    public String categoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categoryForm";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") Category theCategory) {
        categoryService.saveCategory(theCategory);
        return "redirect:/categories/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) throws NullPointerException {
        categoryService.deleteCategory(id);
        return "redirect:/categories/list";
    }
}