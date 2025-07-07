package com.example.demo.domain.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final String TOPIC = "demo-topic-messages";

    @KafkaListener(topics = TOPIC, groupId = "demo-group")
    public void consumeMessage(String message) {
        System.out.printf("Message received -> %s%n", message);
    }
}