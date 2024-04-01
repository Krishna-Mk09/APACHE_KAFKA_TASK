package com.solix.com.consumer_service_one.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.kafka.annotation.KafkaListener;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.regex.Pattern;

/*
 * Author Name : M.V.Krishna
 * Date: 24-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
public interface ConsumerService {

    @KafkaListener(topics = "EmployeeProducer", groupId = "group_producer")
    void listen(String jsonData, String keyToMask);

    String encrypt(final String column) throws Exception;
}
