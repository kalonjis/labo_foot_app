package com.labospring.LaboFootApp.bll.exceptions;

public class DoesntExistsException extends LaboFootException{

    public DoesntExistsException(String message) {
        super(message, 404);
    }

    public DoesntExistsException(String message, int status) {
        super(message, status);
    }
}
