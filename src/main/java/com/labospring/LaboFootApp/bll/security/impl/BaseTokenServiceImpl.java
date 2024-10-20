package com.labospring.LaboFootApp.bll.security.impl;

import com.labospring.LaboFootApp.bll.exceptions.DoesntExistsException;
import com.labospring.LaboFootApp.bll.security.BaseTokenService;
import com.labospring.LaboFootApp.dl.entities.BaseToken;
import com.labospring.LaboFootApp.dl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor
public abstract class BaseTokenServiceImpl<T extends BaseToken> implements BaseTokenService<T> {

    private final JpaRepository<T, Long> tokenRepository;

    @Override
    public T getOne(String token) {
        return tokenRepository.findAll().stream()
                .filter(t -> t.getToken().equals(token))
                .findFirst()
                .orElseThrow(() -> new DoesntExistsException("Token not found"));
    }

    @Override
    public T createToken(User user, Class<T> tokenClass, Long existingtime) {
        String token = generateToken();
        try {
            T t = tokenClass.getDeclaredConstructor().newInstance();
            t.setUser(user);
            t.setToken(token);
            t.setExpiryDate(LocalDateTime.now().plusSeconds(existingtime));
            return tokenRepository.save(t);
        } catch (Exception e) {
            throw new DoesntExistsException ("Token doesn't exist");
        }
    }

    @Override
    public T generateNewToken(String token, Class<T> tokenClass, Long existingtime) {
        T existingToken = getOne(token);
        User user = existingToken.getUser();
        deleteByToken(token);
        return createToken(user, tokenClass, existingtime);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void deleteByToken(String token){
        T existingToken = getOne(token);
        tokenRepository.deleteById(existingToken.getId());
    }
}
