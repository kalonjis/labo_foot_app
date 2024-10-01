package com.labospring.LaboFootApp.pl.models.tournament;

import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import com.labospring.LaboFootApp.il.annotation.ValidTournamentStatus;
import jakarta.validation.constraints.NotNull;

public record TournamentStatusForm(
        @NotNull
        @ValidTournamentStatus
        TournamentStatus tournamentStatus
) {
}
