package com.solix.com.producer_service.service;

import com.solix.com.producer_service.configuration.Constants;
import com.solix.com.producer_service.repository.CustomerRepositoryJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepositoryJdbcTemplate customerRepositoryJdbcTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public CustomerServiceImpl(CustomerRepositoryJdbcTemplate customerRepositoryJdbcTemplate, KafkaTemplate<String, String> kafkaTemplate) {
        this.customerRepositoryJdbcTemplate = customerRepositoryJdbcTemplate;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        List<Map<String, Object>> result = new ArrayList<>();
        String runId = String.valueOf(System.currentTimeMillis());
        List<Map<String, Object>> customers = customerRepositoryJdbcTemplate.findAll();
        List<Map<String, Object>> records = new ArrayList<>();
        for (Map<String, Object> customer : customers) {
            Map<String, Object> record = new HashMap<>();
            for (Map.Entry<String, Object> entry : customer.entrySet()) {
                String columnName = entry.getKey();
                Object columnValue = entry.getValue();
                record.put(columnName, columnValue);
            }
            records.add(record);
        }
        List<String> dataTypes = new ArrayList<>();
        dataTypes.add("VARCHAR");
        dataTypes.add("INTEGER");
        String schemaName = "producers";

        Map<String, Object> tableData = new LinkedHashMap<>();
        tableData.put("runId", runId);
        tableData.put("schema", schemaName);
        tableData.put("dataTypes", dataTypes);
        tableData.put("records", records);
        result.add(tableData);
        kafkaTemplate.send(Constants.Topic, "EmployeeProducers", "dataSent");
        System.out.println("sent data to EmployeeProducers !!!");
        return result;
    }
}
