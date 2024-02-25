package com.solix.com.producer_service.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.solix.com.producer_service.configuration.Constants;
import com.solix.com.producer_service.domain.Customer;
import com.solix.com.producer_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate, CustomerRepository customerRepository) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.customerRepository = customerRepository;
    }

    /**
     * Publishes a list of customers to a Kafka topic.
     * Each customer object is converted to a string representation and sent to the Kafka topic.
     *
     * @param customers The list of Customer objects to be published to the Kafka topic.
     * @return A message indicating the success of publishing the customers to the Kafka topic.
     */
    @Override
    public String addCustomers(List<Customer> customers) {
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                try {
                    String jsonCustomer = objectMapper.writeValueAsString(customer);
                    kafkaTemplate.send(Constants.Topic, jsonCustomer);
                    System.out.println("Published customer successfully: " + customer.getCustomerId());
                } catch (JsonProcessingException e) {
                    System.err.println("Error serializing customer: " + e.getMessage());
                }
            }
        }
        return "Customers added successfully to Kafka topic";
    }


    @Override
    public String addCustomer(Customer customer) {
        this.customerRepository.save(customer);
        return "Customer added successfully";
    }


}
