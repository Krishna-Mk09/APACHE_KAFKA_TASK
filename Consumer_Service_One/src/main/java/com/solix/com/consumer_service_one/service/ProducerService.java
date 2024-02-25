//package com.solix.com.consumer_service_one.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///*
// * Author Name : M.V.Krishna
// * Date: 25-02-2024
// * Created With: IntelliJ IDEA Ultimate Edition
// */
//@Component
//@Service
//public class ProducerService implements ConsumerService {
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @KafkaListener(topics = "producerservice", groupId = "group_producer")
//    public void listen(String jsonData) {
//        System.out.println("Received JSON data from Kafka: " + jsonData);
//        String regexToMask = "\\b[0-9]+\\b";
//        String maskedData = maskData(jsonData, regexToMask);
//        System.out.println("Masked JSON data: " + maskedData);
//
//        kafkaTemplate.send("maskedDataTopic", maskedData);
//        System.out.println("message sent to service 3 : "+ maskedData );
//
//    }
//
//    private String maskData(String jsonData, String regexToMask) {
//        Pattern pattern = Pattern.compile(regexToMask);
//        Matcher matcher = pattern.matcher(jsonData);
//        return matcher.replaceAll("*****");
//    }
//
//    private void maskValue(Object obj, Pattern regexPattern) {
//        // The maskValue method remains the same
//        // You can implement sending masked JSON data for nested objects if needed
//    }
//}
