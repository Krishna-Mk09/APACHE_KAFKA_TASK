package com.solix.com.producer_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solix.com.producer_service.configuration.Constants;
import com.solix.com.producer_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public CustomerController(CustomerService customerService, KafkaTemplate<String, String> kafkaTemplate) {
        this.customerService = customerService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Map<String, Object>>> getAllCustomers() {
        List<Map<String, Object>> customers = customerService.getAllCustomers();
        // Send data to Kafka topic
        ObjectMapper objectMapper = new ObjectMapper();
        for (Map<String, Object> customer : customers) {
            try {
                String customerJson = objectMapper.writeValueAsString(customer);
                kafkaTemplate.send(Constants.Topic, customerJson);
            } catch (JsonProcessingException e) {
                // Handle the exception if unable to serialize to JSON
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
