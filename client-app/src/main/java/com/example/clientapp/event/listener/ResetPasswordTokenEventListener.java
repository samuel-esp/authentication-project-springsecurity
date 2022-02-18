package com.example.clientapp.event.listener;

import com.example.clientapp.dto.EmailLinkDto;
import com.example.clientapp.event.ResetPasswordTokenEvent;
import com.example.clientapp.kafka.ResetPasswordProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResetPasswordTokenEventListener implements ApplicationListener<ResetPasswordTokenEvent> {

    @Autowired
    private ResetPasswordProducer resetPasswordProducer;

    @Override
    public void onApplicationEvent(ResetPasswordTokenEvent event) {

        String url = event.getApplicationUrl() + "/savePassword?token=" + event.getToken();
        EmailLinkDto emailLinkDto = EmailLinkDto.builder()
                        .email(event.getUser().getEmail())
                        .url(url)
                        .build();
        resetPasswordProducer.publishMessage(emailLinkDto);
        log.info("Password Reset Link: " + url);

    }
}
