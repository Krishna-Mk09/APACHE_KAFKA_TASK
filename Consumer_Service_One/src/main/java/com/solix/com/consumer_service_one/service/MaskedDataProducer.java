package com.solix.com.consumer_service_one.service;

/*
 * Author Name : M.V.Krishna
 * Date: 25-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaskedDataProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MaskedDataProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMaskedData(String topic, String maskedData) {
        kafkaTemplate.send(topic, maskedData);
        System.out.println("Masked JSON data sent to Kafka topic: " + topic);
    }
}
