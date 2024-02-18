package com.solix.com.producer_service.service;

import com.solix.com.producer_service.domain.Customer;

import java.util.List;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
public interface CustomerService {
    public String addCustomers(List<Customer> customers);

    public String addCustomer(Customer customer);

    public String updateCustomer(Customer customer);

    public String deleteCustomer(int customerId);

    public Customer getCustomer(int customerId);

    public List<Customer> getAllCustomers();
}
