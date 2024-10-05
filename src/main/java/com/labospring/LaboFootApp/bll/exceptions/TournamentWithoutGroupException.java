package com.labospring.LaboFootApp.bll.exceptions;

public class TournamentWithoutGroupException extends LaboFootException {

    public TournamentWithoutGroupException(String message) {super(message, 404);}
    public TournamentWithoutGroupException(String message, int status) {super(message, status);}
}
