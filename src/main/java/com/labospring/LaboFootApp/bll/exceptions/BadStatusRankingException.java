package com.labospring.LaboFootApp.bll.exceptions;


public class BadStatusRankingException extends LaboFootException {

    public BadStatusRankingException(String message) {
        super(message, 400);
    }

    public BadStatusRankingException(String message, int status) {
        super(message, status);
    }
}