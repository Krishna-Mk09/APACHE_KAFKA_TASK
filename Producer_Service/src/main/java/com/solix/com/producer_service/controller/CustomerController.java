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

    /**
     * Retrieves table data using a SQL query and returns it as an HTTP response.
     * It retrieves table data using the {@code getTableData()} method from the {@code customerService}.
     *
     * @return ResponseEntity containing retrieved table data
     */
    @GetMapping("/findAllUsingSqlQuery")
    public ResponseEntity<?> findAllUsingSqlQuery() {
        TableData tableData = customerService.getTableData();
        return new ResponseEntity<>(tableData, HttpStatus.OK);
    }

    /**
     * Sends table data to Kafka and returns the result as an HTTP response.
     * It invokes the {@code sendTableDataToKafka()} method from the {@code customerService} to send the table data to Kafka.
     *
     * @return ResponseEntity containing the result of sending table data to Kafka
     */
    @GetMapping("/sendTableDataToKafka")
    public ResponseEntity<?> sendTableDataToKafka() {
        String result = customerService.sendTableDataToKafka();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
