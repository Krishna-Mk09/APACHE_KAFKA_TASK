package com.solix.com.producer_service.controller;

import com.solix.com.producer_service.domain.Customer;
import com.solix.com.producer_service.domain.TableData;
import com.solix.com.producer_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * This method adds customers customer data into database
     *
     * @return response entity.
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        return new ResponseEntity(this.customerService.addCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/findAllUsingSqlQuery")
    public ResponseEntity<?> findAllUsingSqlQuery() {
        TableData tableData = customerService.getTableData();
        return new ResponseEntity<>(tableData, HttpStatus.OK);
    }

    @GetMapping("/sendTableDataToKafka")
    public ResponseEntity<?> sendTableDataToKafka() {
        String result = customerService.sendTableDataToKafka();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
