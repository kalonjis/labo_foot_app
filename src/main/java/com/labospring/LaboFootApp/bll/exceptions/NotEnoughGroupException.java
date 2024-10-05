package com.labospring.LaboFootApp.bll.exceptions;

public class NotEnoughGroupException extends LaboFootException {
    public NotEnoughGroupException(String message) {super(message, 404);}
    public NotEnoughGroupException(String message, int status) {super(message, status);}
}
