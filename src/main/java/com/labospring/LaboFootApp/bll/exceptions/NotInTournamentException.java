package com.labospring.LaboFootApp.bll.exceptions;

public class NotInTournamentException extends LaboFootException {

    public NotInTournamentException(String message) {
        super(message, 404);
    }

    public NotInTournamentException(String message, int status) {
        super(message, status);
    }
}