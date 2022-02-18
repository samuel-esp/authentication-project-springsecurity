package com.example.clientapp.event.listener;

import com.example.clientapp.dto.EmailLinkDto;
import com.example.clientapp.event.RegistrationCompletedEvent;
import com.example.clientapp.kafka.UserCreatedProducer;
import com.example.clientapp.model.User;
import com.example.clientapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationCompletedEventListener implements ApplicationListener<RegistrationCompletedEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCreatedProducer userCreatedProducer;

    @Override
    public void onApplicationEvent(RegistrationCompletedEvent event) {

        //Create and Save Token
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveToken(token, user);

        //Send Token To User
        String url = event.getApplicationUrl() + "/verify?token=" + token;

        EmailLinkDto emailLinkDto = EmailLinkDto.builder()
                        .email(user.getEmail())
                        .url(url)
                        .build();

        userCreatedProducer.publishMessage(emailLinkDto);
        log.info("Verification Link: " + url);

    }
}
