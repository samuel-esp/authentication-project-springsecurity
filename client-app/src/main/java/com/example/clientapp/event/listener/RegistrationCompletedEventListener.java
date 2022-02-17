package com.example.clientapp.event.listener;

import com.example.clientapp.event.RegistrationCompletedEvent;
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

    @Override
    public void onApplicationEvent(RegistrationCompletedEvent event) {

        //Create and Save Token
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.saveToken(token, user);

        //Send Token To User
        String url = event.getApplicationUrl() + "/verify?token=" + token;
        log.info("Verification Link: " + url);

    }
}
