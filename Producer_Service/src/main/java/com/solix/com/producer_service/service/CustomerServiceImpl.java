package com.solix.com.producer_service.service;

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
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(KafkaTemplate<String, String> kafkaTemplate, CustomerRepository customerRepository) {
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
                kafkaTemplate.send(Constants.Topic, customer.toString());
                System.out.println("Published successfully");
            }
        }
        return "Customers added successfully to Queue ";
    }


    @Override
    public String addCustomer(Customer customer) {
        this.customerRepository.save(customer);
        return "Customer added successfully";
    }

    @Override
    public String updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public String deleteCustomer(int customerId) {
        return null;
    }

    @Override
    public Customer getCustomer(int customerId) {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }
}
