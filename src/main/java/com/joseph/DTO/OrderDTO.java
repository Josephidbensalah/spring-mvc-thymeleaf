package com.joseph.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class OrderDTO {
    private Long id;
    private Long customerId;
    private List<OrderItemDTO> orderItems = new ArrayList<>();

    // private List<Long> productIds; // List to hold product IDs
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date orderDate;
    private boolean shipped;

    public OrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long id) {
        this.customerId = id;
    }

    // public List<Long> getProductIds() {return productIds;}

    // public void setProductIds(List<Long> productIds) {this.productIds = productIds;}

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderItems" + orderItems +
                ", orderDate=" + orderDate +
                ", shipped=" + shipped +
                '}';
    }
}
