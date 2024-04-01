package com.solix.com.producer_service.configuration;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

/*
 * Author Name : M.V.Krishna
 * Date: 17-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Configures and provides a KafkaTemplate for sending messages to Kafka topics.
     *
     * @return A KafkaTemplate instance configured with the default producer factory.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Configures and provides a default Kafka producer factory.
     *
     * @return A DefaultKafkaProducerFactory instance configured with the specified properties.
     */
    @Bean
    public DefaultKafkaProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
