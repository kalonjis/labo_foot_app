package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.dl.entities.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class UserRegisteredEvent extends ApplicationEvent {

    private final User user;

    public UserRegisteredEvent(Object source, User user){
        super(source);
        this.user = user;
    }
}
