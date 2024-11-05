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

import static com.labospring.LaboFootApp.il.props.LaboFootProps.BACK_URL;
import static com.labospring.LaboFootApp.il.props.LaboFootProps.FRONT_URL;

@Service
@RequiredArgsConstructor
public class MailerServiceImpl implements MailerService {

    private final MailerUtils mailerUtils;
    private final UserVerificationTokenServiceImpl userVerificationTokenService;
    private final PasswordResetTokenServiceImpl passwordResetTokenService;

    @Async
    @Override
    public void sendEmailVerification(User user) {
        UserVerificationToken verificationToken = userVerificationTokenService.createToken(user, UserVerificationToken.class, 20L);
        String confirmationUrl = BACK_URL + "/registrationConfirm?token=" + verificationToken.getToken();
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("url", confirmationUrl);

        mailerUtils.sendMail("Email Verification", "signUpConfirmation", context, user.getEmail());
    }

    @Async
    @Override
    public void sendWelcomeEmail(User user) {
        Context context = new Context();
        context.setVariable("username", user.getUsername());

        mailerUtils.sendMail("Welcome to Tournament Manager", "GreetingComfirmedUser", context, user.getEmail());
    }

    @Async
    @Override
    public void sendNewConfirmation(String token){
        User user = userVerificationTokenService.getOne(token).getUser();
        String confirmationUrl = BACK_URL + "/registrationConfirm?token=" + token;
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("url", confirmationUrl);

        mailerUtils.sendMail("Confirm your Account", "NewConfirmationRequest", context, user.getEmail());
    }


    @Async
    @Override
    public void sendPasswordResetEmail(String token) {
        User user = passwordResetTokenService.getOne(token).getUser();
        String resetUrl = FRONT_URL + "/user/reset-password?token=" + token;
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
