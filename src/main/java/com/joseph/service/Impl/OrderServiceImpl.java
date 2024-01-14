package com.joseph.service.Impl;

import com.joseph.entity.Customer;
import com.joseph.entity.Order;
import com.joseph.repository.CustomerRepository;
import com.joseph.repository.OrderRepository;
import com.joseph.service.CustomerService;
import com.joseph.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
// import com.joseph.exception.ResourceNotFoundException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

//    @Autowired
//    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order getOrderById(Long id) throws NullPointerException {
        return orderRepository.findById(id).orElseThrow(
                () -> new NullPointerException("Order with ID = ${id} is not found"));
    }

    @Override
    @Transactional
    public void deleteOrderById(Long theId) {
        orderRepository.deleteById(theId);
    }

    @Override
    @Transactional
    public void deleteOrder(Order order) {
        if (order != null) {
            Customer customer = order.getCustomer();

            // Remove associations
            order.getOrderItems().forEach(orderItem -> orderItem.setOrder(null));
            order.getOrderItems().clear();

            // Remove the order from the customer's orders
            customer.getOrders().remove(order);

            // Save the customer to update the changes
            customerRepository.save(customer);

            // Delete the order
            orderRepository.delete(order);
        }
    }

}