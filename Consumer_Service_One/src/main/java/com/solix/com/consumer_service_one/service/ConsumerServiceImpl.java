package com.solix.com.consumer_service_one.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Override
    @KafkaListener(topics = "producerservice", groupId = "group_producer")
    public void listen(String jsonData) {
        System.out.println("Received JSON data from Kafka: " + jsonData);
        String regexToMask = "\\b[0-9]+\\b";
        String maskedData = maskData(jsonData, regexToMask);
        System.out.println("Masked JSON data: " + maskedData);
    }

    @Override
    public String maskData(String jsonData, String regexToMask) {
        Pattern pattern = Pattern.compile(regexToMask);
        Matcher matcher = pattern.matcher(jsonData);
        return matcher.replaceAll("*****");
    }

    @Override
    public void maskValue(Object obj, Pattern regexPattern) {
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            for (String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                if (value instanceof String) {
                    String strValue = (String) value;
                    Matcher matcher = regexPattern.matcher(strValue);
                    if (matcher.find()) {
                        jsonObject.put(key, matcher.replaceAll("*****"));
                    }
                } else if (value instanceof JSONObject || value instanceof JSONArray) {
                    maskValue(value, regexPattern);
                }
            }
        } else if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.length(); i++) {
                maskValue(jsonArray.get(i), regexPattern);
            }
        }
    }
}
