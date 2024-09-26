package com.labospring.LaboFootApp.bll.exceptions;

public class MultipleStagesException extends LaboFootException {
    public MultipleStagesException(String message) {
        super(message, 400);
    }
}
