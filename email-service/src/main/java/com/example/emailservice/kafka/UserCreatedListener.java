package com.example.emailservice.kafka;

import com.example.emailservice.dto.EmailLinkDto;
import com.example.emailservice.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserCreatedListener {

    private static final String USER_CREATED_TOPIC = "user-created-topic";
    private static final String GROUP_ID = "group-id";

    @Autowired
    private EmailSenderService emailSenderService;

    @KafkaListener(topics = USER_CREATED_TOPIC, groupId = GROUP_ID)
    public void consumeMessage(EmailLinkDto emailLinkDto){
        emailSenderService.sendWelcomeEmail(emailLinkDto);
    }

}
