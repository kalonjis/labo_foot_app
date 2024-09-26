package com.labospring.LaboFootApp.bll.exceptions;

public class SameTeamException extends LaboFootException {

    public SameTeamException(String message) {
        super(message, 400);
    }

    public SameTeamException(String message, int status) {
        super(message, status);
    }
}