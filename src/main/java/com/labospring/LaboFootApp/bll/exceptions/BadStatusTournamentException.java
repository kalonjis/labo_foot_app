package com.labospring.LaboFootApp.bll.exceptions;


public class BadStatusTournamentException extends LaboFootException {

    public BadStatusTournamentException(String message) {
        super(message, 400);
    }

    public BadStatusTournamentException(String message, int status) {
        super(message, status);
    }
}