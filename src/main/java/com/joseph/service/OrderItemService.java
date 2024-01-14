package com.joseph.service;

import com.joseph.entity.Customer;
import com.joseph.entity.OrderItem;

import java.util.List;
// import com.joseph..exception.ResourceNotFoundException;

public interface OrderItemService {

    OrderItem saveOrderItem(OrderItem orderItem);
    OrderItem getOrderItemById(Long id);
    List<OrderItem> getAllOrderItems();
    void deleteOrderItem(Long id);

    void removeProductReference(Long productId);
}