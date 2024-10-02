package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Event triggered when the status of a football match is updated.
 * This event is used to notify listeners (e.g., WebSocket or other services) about the status update.
 */
@Getter
public class StatusUpdateEvent extends ApplicationEvent {

    private final FootMatch match;

    /**
     * Constructs a new StatusUpdateEvent.
     *
     * @param source    The object on which the event initially occurred (usually the service or component triggering the event).
     * @param footMatch The football match whose status has been updated.
     */
    public StatusUpdateEvent(Object source, FootMatch footMatch) {
        super(source);
        this.match = footMatch;
    }
}

