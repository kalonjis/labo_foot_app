package com.labospring.LaboFootApp.bll.events;

import com.labospring.LaboFootApp.dl.entities.FootMatch;
import com.labospring.LaboFootApp.dl.entities.User;

public interface WebSocketNotifier {

    void sendFootMatchToUser(User user, FootMatch match, String message);

    void setStatusFootMatchUser(User user, FootMatch match, String message);
}
