package com.solix.com.producer_service.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepositoryJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    public CustomerRepositoryJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String, Object>> findAll() {
        String sql = "SELECT * FROM customer";
        return jdbcTemplate.queryForList(sql);
    }
}
