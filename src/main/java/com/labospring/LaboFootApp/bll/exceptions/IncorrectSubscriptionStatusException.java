package com.labospring.LaboFootApp.bll.exceptions;

public class IncorrectSubscriptionStatusException extends LaboFootException {
    public IncorrectSubscriptionStatusException(String message) {
        super(message, 400);
    }

    public IncorrectSubscriptionStatusException(String message, int status) {super(message, status);}
}
