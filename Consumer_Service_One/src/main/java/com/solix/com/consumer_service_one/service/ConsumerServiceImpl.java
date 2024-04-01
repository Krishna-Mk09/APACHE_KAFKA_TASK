package com.solix.com.consumer_service_one.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Component
@Service
public class ConsumerServiceImpl implements ConsumerService {
    SecureRandom randomObj = new SecureRandom();
    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ConsumerServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @KafkaListener(topics = "EmployeeProducer", groupId = "group_producer")
    public void listen(String jsonData, String keyToMask) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonData);
            if (rootNode.has("records")) {
                ArrayNode records = (ArrayNode) rootNode.get("records");
                records.forEach(recordNode -> {
                    recordNode.fields().forEachRemaining(entry -> {
                        String fieldName = entry.getKey();
                        JsonNode fieldValue = entry.getValue();
                        try {
                            if (fieldValue.isTextual()) {
                                String shuffledValue = encrypt(fieldValue.asText());
                                ((ObjectNode) recordNode).put(fieldName, shuffledValue);
                            }
                        } catch (Exception e) {
                            logger.error("Error encrypting field '{}': {}", fieldName, e.getMessage());
                        }
                    });
                });
                kafkaTemplate.send("updatedTopic", rootNode.toString());
                logger.info("Modified JSON data sent to updatedTopic: {}", rootNode);
            } else {
                logger.warn("Received JSON message without records: {}", jsonData);
            }
        } catch (JsonProcessingException e) {
            logger.warn("Received non-JSON message: {}", jsonData);
        }
    }

    @Override
    public String encrypt(String string) throws Exception {
        SecureRandom random = randomObj;
        if (string != null) {
            string = string.trim();
            char[] arrc = string.toCharArray();
            int n = arrc.length;
            for (int i = n - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                char temp = arrc[i];
                arrc[i] = arrc[j];
                arrc[j] = temp;
            }
            return new String(arrc);
        }
        return string;
    }
}
