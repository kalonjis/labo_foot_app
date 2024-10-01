package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import org.springframework.context.ApplicationEvent;

public class ScoreUpdateEvent extends ApplicationEvent {

    private final FootMatch match;

    public ScoreUpdateEvent(Object source, FootMatch match) {
        super(source);
        this.match = match;
    }

    public FootMatch getMatch() {
        return match;
    }
}

