package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.dl.entities.Team;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class TeamForfeitedEvent extends ApplicationEvent {
    private final Team team;
    private final Tournament tournament;

    public TeamForfeitedEvent(Object source, Team team, Tournament tournament ){
        super(source);
        this.team = team;
        this.tournament = tournament;
    }
}
