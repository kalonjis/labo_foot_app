package com.labospring.LaboFootApp.il.validators;

import com.labospring.LaboFootApp.bll.exceptions.*;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import lombok.Getter;

@Getter
public class DispatchingTeamsValidator {

    private Tournament tournament;
    private boolean dispatchable;
    private LaboFootException tournamentNotdispatchableException;


    public DispatchingTeamsValidator(Tournament tournament){
        this.tournament = tournament;

        if(!tournament.getTournamentType().isGroupStage()){
            this.dispatchable = false;
            this.tournamentNotdispatchableException = new TournamentWithoutGroupException("The tournament does not have a group stage.", 400);

        }else if(tournament.getTournamentType().getNbGroups() < 2){
            this.dispatchable = false;
            this.tournamentNotdispatchableException = new NotEnoughGroupException("there is no need to dispatch teams into different groups, as there is only one group", 422);

        }else if (tournament.getTournamentType().getGroups().isEmpty()){
            this.dispatchable = false;
            this.tournamentNotdispatchableException = new IncorrectGroupsInitException("The tournament does not have any groups defined.");

        }else if(tournament.getRankingList() == null){
            this.dispatchable = false;
            this.tournamentNotdispatchableException = new IncorrectRankingListSize("The ranking list for this tournament has not been initialized.", 500);

        }else if (tournament.getRankingList().size() != tournament.getTournamentType().getNbTeams()) {
            this.dispatchable = false;
            this.tournamentNotdispatchableException = new IncorrectRankingListSize("The number of accepted teams does not match the required number for this tournament.");

        }else if (tournament.getTournamentStatus() != TournamentStatus.BUILDING &&
                    tournament.getTournamentStatus() != TournamentStatus.PENDING
                ){
            this.dispatchable = false;
            this.tournamentNotdispatchableException = new IncorrectTournamentStatusException("The tournament status has to be 'BUILDING' or 'PENDING' !");

        }else{
            this.dispatchable = true;
            this.tournamentNotdispatchableException = null;
        }
    }

}
