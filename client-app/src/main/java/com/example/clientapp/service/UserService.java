package com.example.clientapp.service;

import com.example.clientapp.dto.ChangePasswordDto;
import com.example.clientapp.dto.UserDto;
import com.example.clientapp.model.User;
import com.example.clientapp.model.VerificationToken;

public interface UserService {

    User registerUser(UserDto user);

    void saveToken(String token, User user);

    String validateRegistrationToken(String token);

    VerificationToken createNewVerificationToken(String token);

    String changePassword(ChangePasswordDto resetPasswordDto);

    User findUserByEmail(String email);

    void createPasswordResetToken(User user, String token);

    boolean validatePasswordResetToken(String token);

    User findUserByResetToken(String email);
}
