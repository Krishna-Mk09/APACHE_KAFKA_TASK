package com.solix.com.producer_service.service;

import com.solix.com.producer_service.domain.Customer;
import com.solix.com.producer_service.domain.TableData;

import java.util.List;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
public interface CustomerService {
    String addCustomer(Customer customer);

    String sendTableDataToKafka();

    TableData getTableData();

    List<Customer> findAllCustomerUsingSqlQuery();
}
