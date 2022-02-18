package com.example.clientapp.event.listener;

import com.example.clientapp.event.ResendVerificationTokenEvent;
import com.example.clientapp.model.VerificationToken;
import com.example.clientapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResendVerificationTokenEventListener implements ApplicationListener<ResendVerificationTokenEvent>{

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ResendVerificationTokenEvent event) {
        //Create and Save Token
        VerificationToken token = userService.createNewVerificationToken(event.getToken());

        String url = event.getApplicationUrl() + "/verify?token=" + token.getToken();

        log.info("New Verification Link: " + url);
    }
}
