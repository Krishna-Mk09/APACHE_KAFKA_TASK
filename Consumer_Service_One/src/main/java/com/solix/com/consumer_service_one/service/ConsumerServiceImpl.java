package com.solix.com.consumer_service_one.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@Service
public class ConsumerServiceImpl implements ConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ConsumerServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @KafkaListener(topics = "EmployeeProducer", groupId = "group_producer")
    public void listen(String jsonData) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(jsonData);
        rootNode.get("records").forEach(recordNode -> {
            shuffleField(recordNode, "name");
            shuffleField(recordNode, "password");
        });
        logger.info("Modified JSON data: {}", rootNode);
        String modifiedJsonString = rootNode.toString();
        kafkaTemplate.send("updatedTopic", modifiedJsonString);
        System.out.println("sent to updatedTopic ");
    }

    private void shuffleField(JsonNode recordNode, String fieldName) {
        JsonNode fieldNode = recordNode.get(fieldName);
        if (fieldNode != null && fieldNode.isTextual()) {
            String shuffledField = fieldNode.asText().chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), characters -> {
                        Collections.shuffle(characters);
                        return characters.stream().map(String::valueOf).collect(Collectors.joining());
                    }));
            ((ObjectNode) recordNode).put(fieldName, shuffledField);
        }
    }
}
