package com.joseph;

import antlr.collections.List;
import com.joseph.repository.*;
import com.joseph.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer {

    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public DataInitializer(CustomerRepository customerRepository, CategoryRepository categoryRepository,
                           ProductRepository productRepository, OrderRepository orderRepository,
                           OrderItemRepository orderItemRepository) {
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

//    @PostConstruct
    public void init() {
        System.out.println("******************* Inside the Execute method ************");
        // Sample Customers
        Customer customer1 = new Customer("John", "Doe", "john.doe@example.com");
        Customer customer2 = new Customer("Alice", "Smith", "alice.smith@example.com");
        Customer customer3 = new Customer("Bob", "Johnson", "bob.johnson@example.com");
        Customer customer4 = new Customer("Eva", "Brown", "eva.brown@example.com");
        Customer customer5 = new Customer("Charlie", "Wilson", "charlie.wilson@example.com");

        // Save Customers
        customerRepository.saveAll(Stream.of(customer1, customer2, customer3, customer4, customer5)
                .collect(Collectors.toList()));

        // Sample Categories
        Category category1 = new Category("Electronics");
        Category category2 = new Category("Clothing");
        Category category3 = new Category("Books");
        Category category4 = new Category("Furniture");
        Category category5 = new Category("Toys");

        // Save Categories
        categoryRepository.saveAll(Stream.of(category1, category2, category3, category4, category5)
                .collect(Collectors.toList()));

        // Sample Products
        Product product1 = new Product("Laptop", "Powerful laptop for work", new BigDecimal("999.99"), category1,15);
        Product product2 = new Product("T-shirt", "Comfortable cotton t-shirt", new BigDecimal("19.99"), category2,45);
        Product product3 = new Product("Java Book", "Learn Java programming", new BigDecimal("29.99"), category3,7);
        Product product4 = new Product("Sofa", "Elegant living room sofa", new BigDecimal("499.99"), category4,32);
        Product product5 = new Product("Toy Car", "Remote-controlled toy car", new BigDecimal("39.99"), category5,24);

        // Save Products
        productRepository.saveAll(Stream.of(product1, product2, product3, product4, product5)
                .collect(Collectors.toList()));

        // Sample Orders
        Order order1 = new Order(customer1, new Date(), false);
        Order order2 = new Order(customer2, new Date(), false);
        Order order3 = new Order(customer3, new Date(), false);
        Order order4 = new Order(customer4, new Date(), false);
        Order order5 = new Order(customer5, new Date(), false);

        // Save Orders
        orderRepository.saveAll(Stream.of(order1, order2, order3, order4, order5)
                .collect(Collectors.toList()));

        // Sample Order Items
        OrderItem orderItem1 = new OrderItem(order1, product1, 2);
        OrderItem orderItem2 = new OrderItem(order2, product2, 3);
        OrderItem orderItem3 = new OrderItem(order3, product3, 1);
        OrderItem orderItem4 = new OrderItem(order4, product4, 12);
        OrderItem orderItem5 = new OrderItem(order5, product5, 11);

        // Save Order Items
        orderItemRepository.saveAll(
                Stream.of(orderItem1, orderItem2, orderItem3, orderItem4, orderItem5)
                        .collect(Collectors.toList()));
    }
}
