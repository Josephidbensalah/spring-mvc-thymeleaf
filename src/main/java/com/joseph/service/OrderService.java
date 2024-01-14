package com.joseph.service;

import com.joseph.entity.Customer;
import com.joseph.entity.Order;

import java.util.List;
// import com.joseph..exception.ResourceNotFoundException;

public interface OrderService {

    Order saveOrder(Order order);
    Order getOrderById(Long id);
    List<Order> getAllOrders();
    void deleteOrderById(Long id);

    void deleteOrder(Order order);

}