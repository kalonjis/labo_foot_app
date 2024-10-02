package com.labospring.LaboFootApp.bll.exceptions;

public class IncorrectRankingListSize extends LaboFootException {
    public IncorrectRankingListSize(String message) {
        super(message, 409);
    }
}
