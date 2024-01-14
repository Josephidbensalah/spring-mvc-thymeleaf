package com.joseph.service.Impl;

import com.joseph.entity.Customer;
import com.joseph.entity.OrderItem;
import com.joseph.repository.CustomerRepository;
import com.joseph.repository.OrderItemRepository;
import com.joseph.service.CustomerService;
import com.joseph.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// import com.joseph.exception.ResourceNotFoundException;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    @Transactional
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    @Transactional
    public OrderItem getOrderItemById(Long id) throws NullPointerException {
        return orderItemRepository.findById(id).orElseThrow(
                () -> new NullPointerException("OrderItem with ID = ${id} is not found"));
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long theId) {
        orderItemRepository.deleteById(theId);
    }

    @Override
    public void removeProductReference(Long productId) {
        List<OrderItem> orderItems = orderItemRepository.findByProductId(productId);
        for (OrderItem orderItem : orderItems) {
            orderItem.removeProductReference();
            orderItemRepository.save(orderItem);
        }
    }
}