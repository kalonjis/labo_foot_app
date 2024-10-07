package com.labospring.LaboFootApp.bll.validators;

import com.labospring.LaboFootApp.bll.exceptions.IncorrectAcceptedTeamsSizeException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectSubscriptionStatusException;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import lombok.Getter;


@Getter
public class ParticipatingTeamStatusValidator {

    public static void validateNewStatus(ParticipatingTeam participatingTeam, SubscriptionStatus newStatus){

        if(newStatus == null || !SubscriptionStatus.contains(newStatus)){
            throw new IncorrectSubscriptionStatusException(
                    newStatus + " is not a valid SubscriptionStatus. Please use one of the following: {PENDING, ACCEPTED, REJECTED, CANCELED}", 400
                    );
        }
        if (!participatingTeam.getSubscriptionStatus().isValidStatusTransition(newStatus)) {
            throw new IncorrectSubscriptionStatusException(
                    "Transition from " + participatingTeam.getSubscriptionStatus() + " to " + newStatus + " is not allowed.", 400
                    );
        }
        if (newStatus == SubscriptionStatus.ACCEPTED &&
                    participatingTeam.getTournament().getRankingList().size() >=
                        participatingTeam.getTournament().getTournamentType().getNbTeams()
                    ) {
            throw new IncorrectAcceptedTeamsSizeException("The tournament has already accepted the maximum number of participating teams.");
        }
    }
}
