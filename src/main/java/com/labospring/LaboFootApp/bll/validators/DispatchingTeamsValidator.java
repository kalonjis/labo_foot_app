package com.labospring.LaboFootApp.bll.validators;

import com.labospring.LaboFootApp.bll.exceptions.*;
import com.labospring.LaboFootApp.dl.entities.Tournament;
import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import lombok.Getter;

@Getter
public class DispatchingTeamsValidator {

    public static void validateTeamDispatch(Tournament tournament){

        if(!tournament.getTournamentType().isGroupStage()){
           throw new TournamentWithoutGroupException("The tournament does not have a group stage.", 400);

        }
        if(tournament.getTournamentType().getNbGroups() < 2){
            throw new NotEnoughGroupException("there is no need to dispatch teams into different groups, as there is only one group", 422);

        }
        if (tournament.getTournamentType().getGroups().isEmpty()){
            throw new IncorrectGroupsInitException("The tournament does not have any groups defined.");

        }
        if(tournament.getRankingList() == null){
            throw new IncorrectRankingListSize("The ranking list for this tournament has not been initialized.", 500);

        }
        if (tournament.getRankingList().size() != tournament.getTournamentType().getNbTeams()) {
            throw new IncorrectRankingListSize("The number of accepted teams does not match the required number for this tournament.");

        }
        if (tournament.getTournamentStatus() != TournamentStatus.BUILDING &&
                    tournament.getTournamentStatus() != TournamentStatus.PENDING
            ){
            throw new IncorrectTournamentStatusException("The tournament status has to be 'BUILDING' or 'PENDING' !");
        }
    }
}
