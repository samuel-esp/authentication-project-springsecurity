package com.example.clientapp.event;

import com.example.clientapp.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter @Setter
public class RegistrationCompletedEvent extends ApplicationEvent {

    private User user;

    private String applicationUrl;

    public RegistrationCompletedEvent(User user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
