package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ScoreUpdateEvent extends ApplicationEvent {

    private final FootMatch match;

    public ScoreUpdateEvent(Object source, FootMatch match) {
        super(source);
        this.match = match;
    }

}

