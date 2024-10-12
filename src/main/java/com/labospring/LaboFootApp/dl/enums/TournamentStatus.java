package com.labospring.LaboFootApp.dl.enums;

import java.util.Arrays;

public enum TournamentStatus {
    BUILDING,
    PENDING,
    STARTED,
    INTERRUPTED,
    CLOSED,
    CANCELED;

    public static boolean contains(TournamentStatus status) {
        for (TournamentStatus s : values()) {
            if (s == status) {
                return true;
            }
        }
        return false;
    }
    public boolean isValidStatusTransition(TournamentStatus newStatus) {
        return switch (this) {
            case BUILDING -> newStatus == TournamentStatus.PENDING || newStatus == TournamentStatus.CANCELED;
            case PENDING -> newStatus == TournamentStatus.STARTED || newStatus == TournamentStatus.CANCELED;
            case STARTED -> newStatus == TournamentStatus.INTERRUPTED || newStatus == TournamentStatus.CLOSED;
            case INTERRUPTED -> newStatus == TournamentStatus.STARTED || newStatus == TournamentStatus.CANCELED;
            case CLOSED, CANCELED -> false; // Un tournoi fermé ou annulé ne peut plus changer de statut
        };
    }
}
