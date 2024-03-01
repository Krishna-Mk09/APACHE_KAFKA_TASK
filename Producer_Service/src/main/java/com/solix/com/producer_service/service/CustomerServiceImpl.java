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
import java.util.List;
import java.util.UUID;

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
    public String sendTableDataToKafka() {
        TableData tableData = getTableData();
        try {
            String jsonTableData = objectMapper.writeValueAsString(tableData);
            kafkaTemplate.send("producerservice", jsonTableData);
            return "Table data sent successfully to Kafka topic";
        } catch (JsonProcessingException e) {
            log.error("Error serializing table data: {}", e.getMessage());
            return "Failed to send table data to Kafka topic";
        }
    }

    @Override
    public String addCustomer(Customer customer) {
        this.customerRepository.save(customer);
        return "Customer added successfully";
    }


    @Override
    public TableData getTableData() {
        TableData tableData = new TableData();
        Class<?> entityClass = Customer.class;
        Entity entityAnnotation = entityClass.getAnnotation(Entity.class);
        if (entityAnnotation != null) {
            Table tableAnnotation = entityClass.getAnnotation(Table.class);
            if (tableAnnotation != null) {
                String tableName = tableAnnotation.name();
                String schema = tableAnnotation.schema();
                tableData.setTableName(tableName);
                tableData.setSchema(schema);
            }
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation != null) {
                    String columnType = field.getType().getSimpleName();
                    if (tableData.getDataTypes() == null) {
                        tableData.setDataTypes(new ArrayList<>());
                    }
                    tableData.getDataTypes().add(columnType);
                }
            }
        }
        long currentTimeMillis = System.currentTimeMillis();
        UUID uuid = UUID.randomUUID();
        long uniqueId = Math.abs(uuid.getMostSignificantBits() + uuid.getLeastSignificantBits());
        long runId = currentTimeMillis + uniqueId;
        tableData.setRunId(runId);
        List<Customer> records = findAllCustomerUsingSqlQuery();
        tableData.setRecords(records);
        return tableData;
    }


    public List<Customer> findAllCustomerUsingSqlQuery() {
        return customerRepository.findAll();
    }

    @Override
    public String addCustomers(List<Customer> customers) {
        return null;
    }
}
