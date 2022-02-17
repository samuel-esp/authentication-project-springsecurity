package com.example.clientapp.service;

import com.example.clientapp.dto.ChangePasswordDto;
import com.example.clientapp.dto.UserDto;
import com.example.clientapp.model.PasswordResetToken;
import com.example.clientapp.model.User;
import com.example.clientapp.model.VerificationToken;
import com.example.clientapp.repository.PasswordResetTokenRepository;
import com.example.clientapp.repository.UserRepository;
import com.example.clientapp.repository.VerificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    @Transactional
    public User registerUser(UserDto user) {
        if(user.getPassword().equals(user.getMatchingPassword())) {
            User newUser = User.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail().toLowerCase(Locale.ROOT))
                    .password(passwordEncoder.encode(user.getPassword()))
                    .enable(false)
                    .role("USER")
                    .build();

            return userRepository.save(newUser);

        }else{
            return null;
        }
    }

    @Override
    @Transactional
    public void saveToken(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    @Transactional
    public String validateRegistrationToken(String token) {
        Calendar calendar = Calendar.getInstance();
        VerificationToken tokenDb = verificationTokenRepository.findByToken(token);
        User user = tokenDb.getUser();

        if(calendar.getTime().before(tokenDb.getExpirationTime())){
            user.setEnable(true);
            userRepository.save(user);
            return "Valid";
        }else{
            return "Not Valid";
        }

    }

    @Override
    @Transactional
    public VerificationToken createNewVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        Calendar actualTime = Calendar.getInstance();
        actualTime.add(Calendar.MINUTE, 12);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpirationTime(actualTime.getTime());
        verificationTokenRepository.save(verificationToken);

        return verificationToken;
    }

    @Override
    @Transactional
    public String changePassword(ChangePasswordDto resetPasswordDto) {
        User user = userRepository.findByEmail(resetPasswordDto.getEmail());
        if(!passwordEncoder.matches(resetPasswordDto.getNewPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
            userRepository.save(user);
            return "Success";
        }else{
            return "New Password Can't Be The Same As The Old Password";
        }
    }

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void createPasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, token);
        Calendar actualTime = Calendar.getInstance();
        actualTime.add(Calendar.MINUTE, 12);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpirationTime(actualTime.getTime());
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    @Transactional
    public boolean validatePasswordResetToken(String token) {
        Calendar calendar = Calendar.getInstance();
        PasswordResetToken tokenDb = passwordResetTokenRepository.findByToken(token);

        return calendar.getTime().before(tokenDb.getExpirationTime());
    }

    @Override
    @Transactional
    public User findUserByResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken!=null) {
            return passwordResetToken.getUser();
        }else{
            return null;
        }
    }


}
