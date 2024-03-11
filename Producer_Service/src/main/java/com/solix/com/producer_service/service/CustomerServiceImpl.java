package com.solix.com.producer_service.service;

import com.solix.com.producer_service.repository.CustomerRepositoryJdbcTemplate;
import com.solix.com.producer_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepositoryJdbcTemplate customerRepositoryJdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public CustomerServiceImpl(CustomerRepositoryJdbcTemplate customerRepositoryJdbcTemplate, DataSource dataSource) {
        this.customerRepositoryJdbcTemplate = customerRepositoryJdbcTemplate;
        this.dataSource = dataSource;
    }



    private String fetchSchemaName() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            return metaData.getUserName();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> getAllCustomers() {
        List<Map<String, Object>> result = new ArrayList<>();
        String tableName = "customer"; // Change this to the actual table name
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
        List<String> dataTypes = fetchDataTypes(tableName, customers);
        Map<String, Object> tableData = new LinkedHashMap<>();
        tableData.put("runId", runId);
        tableData.put("tableName", tableName);
        String schemaName = fetchSchemaName();
        tableData.put("schema", schemaName);
        tableData.put("dataTypes", dataTypes);
        tableData.put("records", records);
        result.add(tableData);
        return result;
    }

    private List<String> fetchDataTypes(String tableName, List<Map<String, Object>> records) {
        List<String> dataTypes = new ArrayList<>();
        Set<String> columnNames = new HashSet<>();

        // Extract column names from the records
        for (Map<String, Object> record : records) {
            columnNames.addAll(record.keySet());
        }

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                String dataType = resultSet.getString("TYPE_NAME");

                // Check if the column is present in the records
                if (columnNames.contains(columnName)) {
                    dataTypes.add(dataType);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataTypes;
    }


}
