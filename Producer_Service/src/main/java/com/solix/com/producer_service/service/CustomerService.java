package com.solix.com.producer_service.service;
import java.util.List;
import java.util.Map;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
public interface CustomerService {
//    String addCustomer(Customer customer);
//
//    String sendTableDataToKafka();
//
//    TableData getTableData();
//
//    List<Customer> findAllCustomerUsingSqlQuery();

    List<Map<String, Object>> getAllCustomers();
}
