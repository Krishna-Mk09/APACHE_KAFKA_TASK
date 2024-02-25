package com.solix.com.consumer_service_one.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.solix.com.consumer_service_one.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * Author Name : M.V.Krishna
 * Date: 24-02-2024
 * Created With: IntelliJ IDEA Ultimate Edition
 */

@RestController
@RequestMapping("/consumer")
public class MaskingController {
    private final ConsumerService consumerService;

    @Autowired
    public MaskingController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }


}
