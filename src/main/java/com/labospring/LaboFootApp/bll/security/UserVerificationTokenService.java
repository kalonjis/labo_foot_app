package com.labospring.LaboFootApp.bll.security;

import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.entities.UserVerificationToken;

public interface UserVerificationTokenService {

    UserVerificationToken getOne(String token);
    UserVerificationToken createVerificationToken(User user);
    void deleteByToken(String token);
    UserVerificationToken generateNewVerificationToken(String token);
}

