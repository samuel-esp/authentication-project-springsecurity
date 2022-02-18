package com.example.clientapp.kafka;

import com.example.clientapp.dto.EmailLinkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserCreatedProducer{

    private static final String USER_CREATED_TOPIC = "user-created-topic";

    @Autowired
    public KafkaTemplate<String, EmailLinkDto> kafkaTemplate;

    public void publishMessage(EmailLinkDto email) {
        kafkaTemplate.send(USER_CREATED_TOPIC, email);
        log.info("MESSAGE PUBLISHED ON CHANNEL " + USER_CREATED_TOPIC);
    }

}
