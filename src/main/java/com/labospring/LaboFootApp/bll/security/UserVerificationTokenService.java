package com.labospring.LaboFootApp.bll.security;


import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.dal.repositories.UserVerificationTokenRepository;
import com.labospring.LaboFootApp.dl.entities.User;
import com.labospring.LaboFootApp.dl.entities.UserVerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserVerificationTokenService {

    private final UserVerificationTokenRepository userVerificationTokenRepository;

    public UserVerificationToken getOne(String token){
        return userVerificationTokenRepository.findByToken(token).orElseThrow(()-> new DoesntExistsException("this token doesn't match with any user"));
    }

    public UserVerificationToken createVerificationToken(User user){
        String token = generateToken();
        UserVerificationToken userVerificationToken = new UserVerificationToken();
        userVerificationToken.setUser(user);
        userVerificationToken.setToken(token);
        userVerificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));

        return userVerificationTokenRepository.save(userVerificationToken);
    }




    private String generateToken(){
        return UUID.randomUUID().toString();
    }

}
