package com.labospring.LaboFootApp.bll.exceptions;

public class AlreadyExistParticipatingTeamException extends LaboFootException {
    public AlreadyExistParticipatingTeamException(String message) {
        super(message, 409);
    }

    public AlreadyExistParticipatingTeamException(String message, int status) {
        super(message, status);
    }
}
