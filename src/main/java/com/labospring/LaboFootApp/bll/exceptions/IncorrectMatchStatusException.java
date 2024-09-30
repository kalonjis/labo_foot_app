package com.labospring.LaboFootApp.bll.exceptions;

public class IncorrectMatchStatusException extends LaboFootException {
    public IncorrectMatchStatusException(String message) {
        super(message, 400);
    }

    public IncorrectMatchStatusException(String message, int status) {super(message, status);}
}
