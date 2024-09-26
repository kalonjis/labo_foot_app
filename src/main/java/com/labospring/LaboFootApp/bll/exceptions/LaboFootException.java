package com.labospring.LaboFootApp.bll.exceptions;

import lombok.Getter;

@Getter
public class LaboFootException extends RuntimeException{
    private final String message;
    private final int status;

    public LaboFootException(String message, String message1) {
        super(message);
        this.message = message;
        status = 500;
    }

    public LaboFootException(String message, int status) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        StackTraceElement element = this.getStackTrace()[0];
        return String.format("%s" + " thrown in " + "%s" + "() at " + "%s:%d"  + " with message: " + "%s" ,
                this.getClass().getSimpleName(),
                element.getMethodName(),
                element.getFileName(),
                element.getLineNumber(),
                this.getMessage());
    }

}
