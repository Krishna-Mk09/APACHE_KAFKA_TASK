//package com.solix.com.consumer_service_one.service;
//
//import com.solix.com.consumer_service_one.configuration.KafkaConstants;
//import com.solix.com.consumer_service_one.domain.Customer;
//import org.springframework.kafka.annotation.KafkaListener;
//
///*
// * Author Name : M.V.Krishna
// * Date: 17-02-2024
// * Created With: IntelliJ IDEA Ultimate Edition
// */
//public class CustomerServiceImpl implements CustomerService {
//
//
//    @Override
//    @KafkaListener(topics = KafkaConstants.TOPIC, groupId = KafkaConstants.GROUP_ID)
//    public Customer getCustomers(Customer customer) {
//        System.out.println("messages from kafka topic received");
//        return customer;
//    }
//
//
//
//}
