package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Kafkaproducer kafkaProducer;

    @Autowired
    public AdminController(KafkaTemplate<String, String> kafkaTemplate, Kafkaproducer kafkaProducer) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProducer = kafkaProducer;
    }
    @PostMapping("/send-notifications")
    public String sendNotification(@RequestParam("message") String message) {
        System.out.println("Received notification message: " + message);
        
        if (isPoisonMessage(message)) {
            // Send poison message to the dead-letter topic
            kafkaProducer.sendToDeadLetter(message);
            return "Poison message detected and sent to dead-letter topic.";
        }

        kafkaTemplate.sendDefault(message);
        System.out.println("Notification sent to Kafka topic: " + message);
        
        return "Notification sent to administrator: " + message;
    }

    private boolean isPoisonMessage(String message) {
        // Detect poison messages based on the presence of a specific keyword
        return message.contains("POISON");
    }

    
}
