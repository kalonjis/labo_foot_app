package com.labospring.LaboFootApp.bll.exceptions;

public class WrongPasswordException extends LaboFootException {
    public WrongPasswordException(String message) {
        super(message, 409);
    }

    public WrongPasswordException(String message, int status) {
        super(message, status);
    }
}
