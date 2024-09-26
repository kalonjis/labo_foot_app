package com.labospring.LaboFootApp.bll.exceptions;

public class IncorrectTeamSizeException extends LaboFootException {
    public IncorrectTeamSizeException(String message) {
        super(message, 400);
    }
}
