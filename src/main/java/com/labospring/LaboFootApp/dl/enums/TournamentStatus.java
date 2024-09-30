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
}
