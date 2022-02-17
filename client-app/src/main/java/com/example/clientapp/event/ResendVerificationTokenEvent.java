package com.example.clientapp.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter @Setter
public class ResendVerificationTokenEvent extends ApplicationEvent {

    private String token;

    private String applicationUrl;

    public ResendVerificationTokenEvent(String token, String applicationUrl) {
        super(token);
        this.token = token;
        this.applicationUrl = applicationUrl;
    }
}
