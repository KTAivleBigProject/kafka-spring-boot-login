package com.example.demo.domain.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.domain.service.KafkaProducerService;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final KafkaProducerService producerService;

    public DemoController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/message")
    public String publishMessage(@RequestParam String message) {
        producerService.sendMessage(message);
        return "Message published successfully!";
    }
}
