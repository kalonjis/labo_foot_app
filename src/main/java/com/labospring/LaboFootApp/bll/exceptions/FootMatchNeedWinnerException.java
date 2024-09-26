package com.labospring.LaboFootApp.bll.exceptions;

public class FootMatchNeedWinnerException extends LaboFootException {
    public FootMatchNeedWinnerException(String message) {
        super(message, 400);
    }
}
