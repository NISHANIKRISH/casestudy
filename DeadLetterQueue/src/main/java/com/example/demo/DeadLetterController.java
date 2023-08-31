package com.example.demo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeadLetterController {

    private final DeadLetterRepository deadLetterRepository;

    public DeadLetterController(DeadLetterRepository deadLetterRepository) {
        this.deadLetterRepository = deadLetterRepository;
    }

    @GetMapping("/dead-letters")
    public List<Message> getMessages() {
        return deadLetterRepository.findAll();
    }
}
