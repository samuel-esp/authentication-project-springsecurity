package com.example.emailservice.kafka;

import com.example.emailservice.dto.EmailLinkDto;
import com.example.emailservice.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ResetPasswordListener {

    private static final String RESET_PASSWORD_TOPIC = "reset-password-topic";
    private static final String GROUP_ID = "group-id";

    @Autowired
    private EmailSenderService emailSenderService;

    @KafkaListener(topics = RESET_PASSWORD_TOPIC, groupId = GROUP_ID)
    public void consumeMessage(EmailLinkDto emailLinkDto){
        emailSenderService.sendResetPasswordEmail(emailLinkDto);
    }

}
