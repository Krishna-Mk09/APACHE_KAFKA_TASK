package com.solix.com.producer_service.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
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
