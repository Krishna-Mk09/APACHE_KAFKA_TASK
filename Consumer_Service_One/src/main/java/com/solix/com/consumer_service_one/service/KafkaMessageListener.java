package com.solix.com.consumer_service_one.service;

import com.solix.com.consumer_service_one.configuration.KafkaConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "customers", groupId = "group_id")
    public void listen(String message) {
        System.out.println("Received message: " + message);

    }
}

