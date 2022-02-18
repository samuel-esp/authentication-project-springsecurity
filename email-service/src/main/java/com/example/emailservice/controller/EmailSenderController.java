package com.example.emailservice.controller;

import com.example.emailservice.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailSenderController {

    @Autowired
    private EmailSenderService emailSenderService;

    /*@GetMapping("/sendMail")
    public String sendTestMail(){
        emailSenderService.sendWelcomeEmail("TEST");
        return "Email Sent Succesfully";
    }*/

}
