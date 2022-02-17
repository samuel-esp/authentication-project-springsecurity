package com.example.clientapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity @Data
@AllArgsConstructor @NoArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_PASSWORD_RESET_TOKEN"))
    private User user;

    public PasswordResetToken(User user, String token){
        super();
        this.user = user;
        this.token = token;
        this.expirationTime = setExpirationTime();
    }

    public PasswordResetToken(String token){
        super();
        this.token = token;
        this.expirationTime = setExpirationTime();
    }

    public Date setExpirationTime(){
        Calendar actualTime = Calendar.getInstance();
        actualTime.add(Calendar.MINUTE, 15);
        return actualTime.getTime();
    }

}
