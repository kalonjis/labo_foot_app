package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.bll.service.FootMatchService;
import com.labospring.LaboFootApp.bll.service.models.FootMatchSpecificationDTO;
import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.enums.MatchStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MatchCancellationListener {

    private final FootMatchService footMatchService;

    @EventListener
    public void handleTeamForfeited(TeamForfeitedEvent event){

        FootMatchSpecificationDTO teamMatches = new FootMatchSpecificationDTO(
                event.getTeam().getName(),
                null,
                null,
                null,
                null,
                null,
                event.getTournament().getTitle()
        );
        List<FootMatch> matches = footMatchService.getByCriteria(teamMatches);
        matches.stream()
                .forEach(
                        m -> footMatchService.changeStatus(m.getId(), MatchStatus.CANCELED)
                );
    }
}
