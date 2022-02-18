package com.example.emailservice.service;

import com.example.emailservice.config.EmailConfig;
import com.example.emailservice.dto.EmailLinkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableAutoConfiguration
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendWelcomeEmail(EmailLinkDto emailLinkDto){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(EmailConfig.getInstance().getEmailAddress());
        simpleMailMessage.setTo(emailLinkDto.getEmail());
        simpleMailMessage.setText("Hey, Welcome To " + EmailConfig.getInstance().getWebsiteName() +
                "! Follow the link below to complete your registration\n\n"
                + "Link: " + emailLinkDto.getUrl());
        simpleMailMessage.setSubject("Welcome To " + EmailConfig.getInstance().getWebsiteName());

        javaMailSender.send(simpleMailMessage);
        log.info("Welcome Email Sent To User With Email: " + emailLinkDto.getEmail());
    }

    public void sendResetPasswordEmail(EmailLinkDto emailLinkDto) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(EmailConfig.getInstance().getEmailAddress());
        simpleMailMessage.setTo(emailLinkDto.getEmail());
        simpleMailMessage.setText("Reset Your Password " + EmailConfig.getInstance().getWebsiteName());
        simpleMailMessage.setSubject("Follow the link below to reset your password: " + "\n\n" + "Link: " + emailLinkDto.getUrl());

        javaMailSender.send(simpleMailMessage);
        log.info("Reset Password Email Sent To User With Email: " + emailLinkDto.getEmail());

    }
}
