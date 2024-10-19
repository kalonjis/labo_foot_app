package com.labospring.LaboFootApp.bll.security.impl;


import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.security.UserVerificationTokenService;
import com.labospring.LaboFootApp.dal.repositories.UserVerificationTokenRepository;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.entities.UserVerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserVerificationTokenServiceImpl implements UserVerificationTokenService {

    private final UserVerificationTokenRepository userVerificationTokenRepository;

    @Override
    public UserVerificationToken getOne(String token){
        return userVerificationTokenRepository.findByToken(token).orElseThrow(()-> new DoesntExistsException("this token doesn't match with any user"));
    }

    @Override
    public UserVerificationToken createVerificationToken(User user){
        String token = generateToken();
        UserVerificationToken userVerificationToken = new UserVerificationToken();
        userVerificationToken.setUser(user);
        userVerificationToken.setToken(token);
        userVerificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));

        return userVerificationTokenRepository.save(userVerificationToken);
    }

    @Override
    public void deleteByToken(String token){
        UserVerificationToken verificationToken = getOne(token);
        userVerificationTokenRepository.delete(verificationToken);
    }

    @Override
    public UserVerificationToken generateNewVerificationToken(String token) {
        UserVerificationToken currentToken = getOne(token);
        User user = currentToken.getUser();
        deleteByToken(token);
        return createVerificationToken(user);
    }

    private String generateToken(){
        return UUID.randomUUID().toString();
    }

}
