package com.labospring.LaboFootApp.bll.exceptions;

import java.util.HashMap;
import java.util.Map;

public class BadEnabledStatusException extends LaboFootException {
    public BadEnabledStatusException(String message) {
        super(message, 409);
    }

    public BadEnabledStatusException(String message, int status) {
        super(message, status);
    }

    public Map<String,String>  messageToMap(String message) {
        Map<String,String> mapException = new HashMap<>();
        String[] arrMessage = message.split("http");
        if (arrMessage.length > 1) {
            String url = "http" + arrMessage[arrMessage.length - 1];
            String mess = arrMessage[0];
            mapException.put("notEnabledError", mess);
            mapException.put("confirmUrl", url);
            return mapException;
        } else {
            return null;
        }
    }
}
