package com.example.clientapp.event.listener;

import com.example.clientapp.event.ResetPasswordTokenEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResetPasswordTokenEventListener implements ApplicationListener<ResetPasswordTokenEvent> {

    @Override
    public void onApplicationEvent(ResetPasswordTokenEvent event) {

        String url = event.getApplicationUrl() + "/savePassword?token=" + event.getToken();
        log.info("Password Reset Link: " + url);

    }
}
