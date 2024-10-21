package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.security.impl.PasswordResetTokenServiceImpl;
import com.labospring.LaboFootApp.bll.security.impl.UserVerificationTokenServiceImpl;
import com.labospring.LaboFootApp.bll.service.MailerService;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.entities.UserVerificationToken;
import com.labospring.LaboFootApp.il.utils.MailerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailerServiceImpl implements MailerService {

    private final MailerUtils mailerUtils;
    private final UserVerificationTokenServiceImpl userVerificationTokenService;
    private final PasswordResetTokenServiceImpl passwordResetTokenService;

    @Async
    @Override
    public void sendWelcomeEmail(User user) {
        UserVerificationToken verificationToken = userVerificationTokenService.createToken(user, UserVerificationToken.class, 20L);
        String confirmationUrl = "http://localhost:8080/registrationConfirm?token=" + verificationToken.getToken();
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("url", confirmationUrl);

        mailerUtils.sendMail("Welcome to Tournament Manager", "signUpConfirmation", context, user.getEmail());
    }

    @Async
    @Override
    public void sendNewConfirmation(String token){
        User user = userVerificationTokenService.getOne(token).getUser();
        String confirmationUrl = "http://localhost:8080/registrationConfirm?token=" + token;
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("url", confirmationUrl);

        mailerUtils.sendMail("Confirm your Account", "NewConfirmationRequest", context, user.getEmail());
    }


    @Async
    @Override
    public void sendPasswordResetEmail(String token) {
        User user = passwordResetTokenService.getOne(token).getUser();
        //String resetUrl = "http://localhost:8080/reset-password?token=" + token; Use without html page (postman)
        String resetUrl = "http://localhost:8080/reset-password-form?token=" + token;
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("url", resetUrl);

        mailerUtils.sendMail("Password Reset", "NewPasswordRequest", context, user.getEmail());
    }

    @Async
    @Override
    public void sendTournmentNotification(String userEmail, String TournamentTitle, String message) {
        return;
    }
}
