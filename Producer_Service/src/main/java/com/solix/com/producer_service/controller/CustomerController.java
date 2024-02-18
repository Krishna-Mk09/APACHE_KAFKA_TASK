package com.solix.com.producer_service.controller;

import com.solix.com.producer_service.domain.Customer;
import com.solix.com.producer_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Endpoint for adding customers to the system.
     *
     * @param customers A list of Customer objects representing the customers to be added.
     * @return ResponseEntity containing the result of the customer addition operation.
     * Returns HTTP status 201 (Created) if successful, along with any additional information.
     */
    @PostMapping(value = "/addCustomers", consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody List<Customer> customers) {
        return new ResponseEntity(this.customerService.addCustomers(customers), HttpStatus.CREATED);
    }

    /**
     * Endpoint for adding a single customer to the system.
     *
     * @param customer The Customer object representing the customer to be added.
     * @return ResponseEntity containing the result of the customer addition operation.
     * Returns HTTP status 201 (Created) if successful, along with any additional information.
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<?> save(@RequestBody Customer customer) {
        return new ResponseEntity(this.customerService.addCustomer(customer), HttpStatus.CREATED);
    }
}
