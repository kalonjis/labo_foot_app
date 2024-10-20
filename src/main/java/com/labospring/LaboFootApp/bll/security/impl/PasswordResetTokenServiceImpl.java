package com.labospring.LaboFootApp.bll.security.impl;

import com.labospring.LaboFootApp.dal.repositories.PasswordResetTokenRepository;
import com.labospring.LaboFootApp.dl.entities.PasswordResetToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenServiceImpl extends BaseTokenServiceImpl<PasswordResetToken> {

    public PasswordResetTokenServiceImpl(@Qualifier("passwordResetTokenRepository") PasswordResetTokenRepository tokenRepository) {
        super(tokenRepository);
    }
}

