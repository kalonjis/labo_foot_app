package com.labospring.LaboFootApp.bll.security.impl;


import com.labospring.LaboFootApp.dal.repositories.UserVerificationTokenRepository;
import com.labospring.LaboFootApp.dl.entities.UserVerificationToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class UserVerificationTokenServiceImpl extends BaseTokenServiceImpl<UserVerificationToken> {

    public UserVerificationTokenServiceImpl(@Qualifier("userVerificationTokenRepository") UserVerificationTokenRepository tokenRepository) {
        super(tokenRepository);
    }
}
