package com.labospring.LaboFootApp.bll.service.impl;

import com.labospring.LaboFootApp.bll.service.MailerService;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.il.utils.MailerUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailerServiceImpl implements MailerService {

    private final MailerUtils mailerUtils;

    @Async
    @Override
    public void sendWelcomeEmail(User user) {
        Context context = new Context();
        context.setVariable("username", user.getUsername());
//        context.setVariable("usermail", user.getEmail());

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
