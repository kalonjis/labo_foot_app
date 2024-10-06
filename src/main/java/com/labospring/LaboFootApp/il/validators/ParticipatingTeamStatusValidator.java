package com.labospring.LaboFootApp.il.validators;

import com.labospring.LaboFootApp.bll.exceptions.IncorrectAcceptedTeamsSizeException;
import com.labospring.LaboFootApp.bll.exceptions.IncorrectSubscriptionStatusException;
import com.labospring.LaboFootApp.bll.exceptions.LaboFootException;
import com.labospring.LaboFootApp.dl.entities.ParticipatingTeam;
import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import lombok.Getter;


@Getter
public class ParticipatingTeamStatusValidator {

    ParticipatingTeam participatingTeam;
    boolean validStatusChange;
    LaboFootException participatingTeamSatusChangeException;

    public ParticipatingTeamStatusValidator(ParticipatingTeam participatingTeam, SubscriptionStatus newStatus){
        this.participatingTeam = participatingTeam;

        if(newStatus == null || !SubscriptionStatus.contains(newStatus)){
            this.validStatusChange = false;
            this.participatingTeamSatusChangeException = new IncorrectSubscriptionStatusException(
                    newStatus + " is not a valid SubscriptionStatus. Please use one of the following: {PENDING, ACCEPTED, REJECTED, CANCELED}", 400
                    );
        } else if (!participatingTeam.getSubscriptionStatus().isValidStatusTransition(newStatus)) {
            this.validStatusChange = false;
            this.participatingTeamSatusChangeException = new IncorrectSubscriptionStatusException(
                    "Transition from " + participatingTeam.getSubscriptionStatus() + " to " + newStatus + " is not allowed.", 400
                    );
        } else if (newStatus == SubscriptionStatus.ACCEPTED &&
                    participatingTeam.getTournament().getRankingList().size() >=
                        participatingTeam.getTournament().getTournamentType().getNbTeams()
                    ) {
            this.validStatusChange = false;
            this.participatingTeamSatusChangeException = new IncorrectAcceptedTeamsSizeException("The tournament has already accepted the maximum number of participating teams.");

        } else{
            this.validStatusChange = true;
            this.participatingTeamSatusChangeException = null;
        }
    }
}
