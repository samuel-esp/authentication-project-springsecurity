package com.example.clientapp.event;

import com.example.clientapp.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter @Setter
public class ResetPasswordTokenEvent extends ApplicationEvent {

    User user;

    String applicationUrl;

    String token;

    public ResetPasswordTokenEvent(User user, String applicationUrl, String token) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
        this.token = token;
    }


}
