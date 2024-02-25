package com.solix.com.consumer_service_one.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.regex.Pattern;

/*
 * Author Name : M.V.Krishna
 * Date: 24-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */
public interface ConsumerService {
    

    /**
     * Listens for JSON data received from Kafka.
     *
     * @param jsonData The JSON data received from Kafka.
     */
    void listen(String jsonData);

    /**
     * Masks sensitive data in JSON using a regular expression.
     *
     * @param jsonData    The JSON data to be masked.
     * @param regexToMask The regular expression to identify data to mask.
     * @return The masked JSON data.
     */
    String maskData(String jsonData, String regexToMask);

    /**
     * Masks sensitive values in a JSON object recursively.
     *
     * @param obj          The JSON object whose values need to be masked.
     * @param regexPattern The regular expression pattern to identify values to mask.
     */
    void maskValue(Object obj, Pattern regexPattern);
}
