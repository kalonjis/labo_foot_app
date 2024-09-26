package com.labospring.LaboFootApp.bll.exceptions;

public class IncorrectDateException extends LaboFootException {

    public IncorrectDateException(String message) {
        super(message, 400);
    }

    public IncorrectDateException(String message, int status) {
        super(message, status);
    }
}