package com.joseph.web;

import com.joseph.DTO.OrderDTO;
import com.joseph.DTO.ProductDTO;
import com.joseph.entity.*;
import com.joseph.service.CategoryService;
import com.joseph.service.CustomerService;
import com.joseph.service.OrderItemService;
import com.joseph.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping({"/list", "/"})
    public String listProducts(Model theModel) {
        List<Product> products = productService.getAllProducts();
        theModel.addAttribute("products", products);
        return "list-products";
    }

    @GetMapping("/productForm")
    public String productForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "productForm";
    }
    @GetMapping("/productForm/{id}")
    public String productForm(@PathVariable("id") Long id, Model model) {
        ProductDTO productDTO = modelMapper.map(productService.getProductById(id), ProductDTO.class);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories",categoryService.getAllCategories());
        return "productForm";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("productDTO") ProductDTO productDTO) {

        System.out.println("*****************************************************");
        System.out.println("productDTO to be saved : "+productDTO.toString());

        // Convert DTO to Entity
        Product product = convertDTOToEntity(productDTO);
        System.out.println("order : "+product.toString());

        // Save the Product
        productService.saveProduct(product);
        return "redirect:/products/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws NullPointerException {
        try {
            // Remove references in OrderItem entities
            orderItemService.removeProductReference(id);

            productService.deleteProduct(id);
            // Add a flash attribute to carry a message to the redirected view
            redirectAttributes.addFlashAttribute("modalMessage", "Product deleted successfully");


            System.out.println("Product deleted successfully");
            return "redirect:/products/list";
        }catch (Exception e){
            // Add the message attribute to the model
            redirectAttributes.addFlashAttribute("modalMessage", e.getMessage());
//            List<Product> products = productService.getAllProducts();
//            model.addAttribute("products", products);
            return "redirect:/products/list"; // Return the template name
        }

    }

    public Product convertDTOToEntity(ProductDTO productDTO) {
        System.out.println("************ DTO CONVERTER **********");
        // Convert the ProductDTO to a Product entity
        Product product = modelMapper.map(productDTO, Product.class);

        // Convert the CategoryDTO to a Category entity and set it on the order
        Category category = categoryService.getCategoryById(productDTO.getCategoryId());
        product.setCategory(category);
        System.out.println("Product : Category =>  " + product.getCategory().getName());

        System.out.println("Product :   " + product.toString());
        return product;
    }




    }