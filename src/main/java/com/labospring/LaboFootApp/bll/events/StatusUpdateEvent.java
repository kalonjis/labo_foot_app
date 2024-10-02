package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StatusUpdateEvent extends ApplicationEvent {
    private FootMatch match;
    public StatusUpdateEvent(Object source, FootMatch footMatch) {
        super(source);
        this.match = footMatch;
    }
}
