package com.solix.com.producer_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solix.com.producer_service.domain.Customer;
import com.solix.com.producer_service.domain.TableData;
import com.solix.com.producer_service.repository.CustomerRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate, CustomerRepository customerRepository) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.customerRepository = customerRepository;
    }

    @Override
    public String addCustomer(Customer customer) {
        this.customerRepository.save(customer);
        return "Customer added successfully";
    }

    @Override
    public String sendTableDataToKafka() {
        try {
            String jsonTableData = objectMapper.writeValueAsString(getTableData());
            kafkaTemplate.send("EmployeeProducer", jsonTableData);
            return "Table data sent successfully to Kafka topic";
        } catch (JsonProcessingException e) {
            log.error("Error serializing table data: {}", e.getMessage());
            return "Failed to send table data to Kafka topic";
        }
    }

    @Override
    public TableData getTableData() {
        TableData tableData = new TableData();
        Class<?> entityClass = Customer.class;
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            tableData.setTableName(tableAnnotation.name());
            tableData.setSchema(tableAnnotation.schema());
        }
        tableData.setDataTypes(Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getType().getSimpleName())
                .collect(Collectors.toList()));
        tableData.setRunId(System.currentTimeMillis() + Math.abs(UUID.randomUUID().getMostSignificantBits() + UUID.randomUUID().getLeastSignificantBits()));
        tableData.setRecords(findAllCustomerUsingSqlQuery());
        return tableData;
    }

    public List<Customer> findAllCustomerUsingSqlQuery() {
        return customerRepository.findAll();
    }
}
