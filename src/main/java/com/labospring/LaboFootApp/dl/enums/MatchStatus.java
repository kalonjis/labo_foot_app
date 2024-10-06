package com.labospring.LaboFootApp.dl.enums;

public enum MatchStatus {
    SCHEDULED,
    INPROGRESS,
    FINISHED,
    INTERRUPTED,
    CANCELED;

    // Méthode pour valider les transitions de statut de match
    public boolean isValidMatchStatusTransition(MatchStatus newStatus) {
        return switch (this) {
            case SCHEDULED -> newStatus == MatchStatus.INPROGRESS || newStatus == MatchStatus.CANCELED;
            case INPROGRESS -> newStatus == MatchStatus.FINISHED || newStatus == MatchStatus.INTERRUPTED || newStatus == MatchStatus.CANCELED;
            case FINISHED -> false; // Un match terminé ne peut pas changer de statut
            case INTERRUPTED -> newStatus == MatchStatus.INPROGRESS || newStatus == MatchStatus.CANCELED;
            case CANCELED -> false; // Un match annulé ne peut pas changer de statut
        };
    }
}
