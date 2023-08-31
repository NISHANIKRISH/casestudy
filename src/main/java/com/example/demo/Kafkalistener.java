package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
 class MessageConsumer {

    @Autowired
    private Kafkaproducer kafkaProducer;

    @KafkaListener(topics = "${spring.kafka.topic.messages}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        if (isPoisonMessage(message)) {
            // Send poison message to the dead-letter topic
            kafkaProducer.sendWarning("DeadLetter", message);
            return;
        }
        
        // Process the received message
        System.out.println("Received regular message: " + message);
    }
    
    private boolean isPoisonMessage(String message) {
        // Implement your poison message detection logic
        // Return true if it's a poison message, false otherwise
        return false; // Change this condition based on your logic
    }
}

@Component
public class Kafkalistener {

    @Autowired
    private Kafkaproducer kafkaProducer;

    @KafkaListener(topics = "${spring.kafka.topic.warning}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenForWarningMessages(String message) {
        if (isPoisonMessage(message)) {
            // Send poison message to the dead-letter topic
            kafkaProducer.sendWarning("DeadLetter", message);
            return;
        }
        
        // Logic to handle received warning messages
        System.out.println("Received warning message: " + message);
    }

    private boolean isPoisonMessage(String message) {
        // Implement your poison message detection logic
        // Return true if it's a poison message, false otherwise
        return false; // Change this condition based on your logic
    }
}
