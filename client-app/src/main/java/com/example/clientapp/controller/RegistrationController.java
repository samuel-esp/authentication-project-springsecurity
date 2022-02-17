package com.example.clientapp.controller;

import com.example.clientapp.dto.ChangePasswordDto;
import com.example.clientapp.dto.ResetPasswordDto;
import com.example.clientapp.dto.UserDto;
import com.example.clientapp.event.RegistrationCompletedEvent;
import com.example.clientapp.event.ResendVerificationTokenEvent;
import com.example.clientapp.event.ResetPasswordTokenEvent;
import com.example.clientapp.model.User;
import com.example.clientapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping("/api/helloworld")
    public String helloWorld(Principal principal){
        return "hello " + principal.getName();
    }

    @PostMapping("/register")
    public String RegisterUser(@RequestBody UserDto user, final HttpServletRequest request){
        User newUser = userService.registerUser(user);
        if(newUser!=null) {
            publisher.publishEvent(new RegistrationCompletedEvent(newUser, applicationUrl(request)));
            return "User Registered Succesfully";
        }else{
            return  "Passwords Don't Match Try Again";
        }
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @GetMapping("/verify")
    public String verifyUserRegistration(@RequestParam("token") String token){
        String result = userService.validateRegistrationToken(token);
        if(result.equals("Valid")){
            return "User Succesfully Verified";
        }else {
            return "User Was Not Verified";
        }
    }

    @GetMapping("/requestNewToken")
    public String requestNewVerificationToken(@RequestParam ("token") String token, HttpServletRequest request){
        publisher.publishEvent(new ResendVerificationTokenEvent(token, applicationUrl(request)));
        return "New Verification Link Sent";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ChangePasswordDto resetPasswordDto){
        if(resetPasswordDto.getNewPassword().equals(resetPasswordDto.getConfirmNewPassword())) {
            String result = userService.changePassword(resetPasswordDto);
            return result;
        }else{
            return "Passwords Don't Match";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody ResetPasswordDto resetPasswordDto, HttpServletRequest httpServletRequest){
        User user = userService.findUserByEmail(resetPasswordDto.getEmail());
        String url = "";
        if(user!=null){
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetToken(user, token);
            publisher.publishEvent(new ResetPasswordTokenEvent(user, applicationUrl(httpServletRequest), token));
        }else {
            return "User Does Not Exist";
        }
        return "Password Reset Link Sent";
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody ChangePasswordDto changePasswordDto){
        User user = userService.findUserByResetToken(token);
        if(user!=null && userService.validatePasswordResetToken(token)){
            if(changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmNewPassword())){
                String result = userService.changePassword(changePasswordDto);
                return result;
            }else {
                return "Password Don't Match";
            }
        }else{
            return  user!=null ? "Token Expired": "User Does Not Exist";
        }

    }


}
