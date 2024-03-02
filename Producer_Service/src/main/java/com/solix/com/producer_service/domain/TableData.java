package com.solix.com.producer_service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/*
 * Author Name : M.V.Krishna
 * Date: 01-03-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableData {
    private long runId;
    private String tableName;
    private String schema;
    private List<String> dataTypes;
    private List<Customer> records;
}
