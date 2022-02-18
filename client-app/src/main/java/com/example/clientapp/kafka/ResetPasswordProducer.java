package com.example.clientapp.kafka;

import com.example.clientapp.dto.EmailLinkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResetPasswordProducer {

    private static final String RESET_PASSWORD_TOPIC = "reset-password-topic";

    @Autowired
    public KafkaTemplate<String, EmailLinkDto> kafkaTemplate;

    public void publishMessage(EmailLinkDto email){
        kafkaTemplate.send(RESET_PASSWORD_TOPIC, email);
        log.info("MESSAGE PUBLISHED ON CHANNEL " + RESET_PASSWORD_TOPIC);
    }

}
