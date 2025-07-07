package com.example.demo.domain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.service.KafkaProducerService;

@RestController
public class DemoController {

    private final KafkaProducerService producerService;

    public DemoController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/message")
    public String publishMessage(@RequestParam String message) {
        producerService.sendMessage(message);
        return "Message published successfully!";
    }
}