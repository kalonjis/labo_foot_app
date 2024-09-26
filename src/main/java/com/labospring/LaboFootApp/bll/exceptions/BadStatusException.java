package com.labospring.LaboFootApp.bll.exceptions;


public class BadStatusException extends LaboFootException {

    public BadStatusException(String message) {
        super(message, 400);
    }

    public BadStatusException(String message, int status) {
        super(message, status);
    }
}