package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.security.UserVerificationTokenService;
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
    private final UserVerificationTokenService userVerificationTokenService;

    @Async
    @Override
    public void sendWelcomeEmail(User user) {
        UserVerificationToken verificationToken = userVerificationTokenService.createVerificationToken(user);
        String confirmationUrl = "http://localhost:8080/registrationConfirm?token=" + verificationToken.getToken();
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("url", confirmationUrl);

        mailerUtils.sendMail("Welcome to Tournament Manager", "signUpConfirmation", context, user.getEmail());
    }

    @Async
    @Override
    public void sendPasswordResetEmail(String userEmail, String token) {
        return;
    }

    @Async
    @Override
    public void sendTournmentNotification(String userEmail, String TournamentTitle, String message) {
        return;
    }
}
