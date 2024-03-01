package com.solix.com.consumer_service_one.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.regex.Pattern;

/*
 * Author Name : M.V.Krishna
 * Date: 24-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
public interface ConsumerService {

    @KafkaListener(topics = "producerservice", groupId = "group_producer")
    void listen(String jsonData) throws JsonProcessingException;
}
