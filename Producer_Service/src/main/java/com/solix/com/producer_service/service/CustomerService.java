package com.solix.com.producer_service.service;

import com.solix.com.producer_service.domain.Customer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
public interface CustomerService {
    public String addCustomers(List<Customer> customers);

    public String addCustomer(Customer customer);


}
