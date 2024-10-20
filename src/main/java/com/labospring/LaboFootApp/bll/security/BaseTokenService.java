package com.labospring.LaboFootApp.bll.security;

import com.labospring.LaboFootApp.dl.entities.BaseToken;
import com.labospring.LaboFootApp.dl.entities.User;

public interface BaseTokenService <T extends BaseToken> {
    T getOne(String token);
    T createToken(User user, Class<T> tokenClass, Long existingTime);
    T generateNewToken(String token, Class<T> tokenClass, Long existingTime);
}
