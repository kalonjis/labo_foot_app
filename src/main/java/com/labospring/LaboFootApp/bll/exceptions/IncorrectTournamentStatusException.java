package com.labospring.LaboFootApp.bll.exceptions;

public class IncorrectTournamentStatusException extends LaboFootException {
    public IncorrectTournamentStatusException(String message) {
        super(message, 400);
    }

    public IncorrectTournamentStatusException(String message, int status) {super(message, status);}
}
