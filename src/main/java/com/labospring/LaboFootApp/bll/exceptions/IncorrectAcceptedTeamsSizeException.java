package com.labospring.LaboFootApp.bll.exceptions;

public class IncorrectAcceptedTeamsSizeException extends LaboFootException {
    public IncorrectAcceptedTeamsSizeException(String message) {
        super(message, 400);
    }
}
