package com.labospring.LaboFootApp.bll.exceptions;

public class UserCredentialAlreadyTakenException extends LaboFootException {
    public UserCredentialAlreadyTakenException(String message) {
        super(message, 409);
    }

    public UserCredentialAlreadyTakenException(String message, int status) {
        super(message, status);
    }
}
