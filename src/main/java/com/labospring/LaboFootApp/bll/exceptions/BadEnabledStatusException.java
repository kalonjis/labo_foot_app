package com.labospring.LaboFootApp.bll.exceptions;

public class BadEnabledStatusException extends LaboFootException {
    public BadEnabledStatusException(String message) {
        super(message, 409);
    }

    public BadEnabledStatusException(String message, int status) {
        super(message, status);
    }
}
