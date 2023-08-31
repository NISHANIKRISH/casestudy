package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class Kafkaproducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.warning}")
    private String warningTopic; // This is your warning topic for regular messages

    @Value("${spring.kafka.topic.dead-letter}")
    private String deadLetterTopic; // This is your dead-letter topic for poison messages

    @Autowired
    public Kafkaproducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWarning(String clientId, String messageContent) {
        String warningMessage = "Warning: Message for client " + clientId + " couldn't be delivered - " + messageContent;
        kafkaTemplate.send(warningTopic, warningMessage);
    }

    public void sendToDeadLetter(String message) {
        kafkaTemplate.send(deadLetterTopic, message);
    }
}



