package com.labospring.LaboFootApp.bll.service;

import com.labospring.LaboFootApp.dl.entities.User;

public interface MailerService {
    void sendWelcomeEmail(User user);
    void sendPasswordResetEmail(String userEmail, String token);
    void sendTournmentNotification(String userEmail, String TournamentTitle, String message);
}
