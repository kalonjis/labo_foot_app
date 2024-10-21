package com.labospring.LaboFootApp.bll.events;


import com.labospring.LaboFootApp.bll.service.MailerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventListener {

    private final MailerService mailerService;

    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event){

        mailerService.sendEmailVerification(event.getUser());
    }
}
