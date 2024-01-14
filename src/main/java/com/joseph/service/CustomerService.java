package com.joseph.service;

import java.util.List;

import com.joseph.entity.Customer;
// import com.joseph..exception.ResourceNotFoundException;

public interface CustomerService {

    public List<Customer> getCustomers();

    public void saveCustomer(Customer theCustomer);

    public Customer getCustomer(Long theId);

    public void deleteCustomer(Long theId);

}