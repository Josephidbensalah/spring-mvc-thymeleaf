package com.joseph.repository;

import com.joseph.entity.Customer;
import com.joseph.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("orderItemRepository")
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
//    @Transactional
//    void deleteByOrderId(Long orderId);

    List<OrderItem> findByOrderId(Long orderId);

    List<OrderItem> findByProductId(Long productId);
}